package com.example.gateway.filters.pre;

import com.example.gateway.model.Event;
import com.example.gateway.model.Request;
import com.example.gateway.repository.EventRepository;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public class HTTPFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger ( HTTPFilter.class );

    @Autowired
    private EventRepository eventRepository;


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext ();
        HttpServletRequest request = ctx.getRequest ();
        Event event = null;

        LocalDateTime localDateTime = LocalDateTime.now ();
        LocalDateTime localDateTimeFrom = localDateTime.minusSeconds ( 5 );
        LocalDateTime localDateTimeTo = localDateTime.plusSeconds ( 3 );


        List<Event> events = eventRepository.findAllByIpAndDateStartBetweenOrderByFrequencyDesc ( request.getRemoteAddr (), localDateTimeFrom, localDateTimeTo );

        if ( events.size () > 0 ) {
            event = events.get ( 0 );
        } else {
            try {
                Thread.sleep ( 1000 );
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            events = eventRepository.findAllByIpAndDateStartBetweenOrderByFrequencyDesc ( request.getRemoteAddr (), localDateTimeFrom, localDateTimeTo );

            if ( events.size () > 0 ) {
                event = events.get ( 0 );
            }

        }


        if ( event == null ) {
            event = new Event ();
            event.setIp ( request.getRemoteAddr () );
            Request requestObject =  new Request (request.getRequestURI (),request.getMethod ());
            requestObject.setEvent (event);
            event.getRequests ().add ( requestObject );
            event.setStatus ( "USER" );
            event.setFrequency ( 1L );
            event.setDateStart ( localDateTime );
            event.setDateEnd ( localDateTime );
            eventRepository.save ( event );
        } else {
            event.frequencyIncrement ();
            event.setDateEnd ( localDateTime );
            Request requestObject =  new Request (request.getRequestURI (),request.getMethod ());
            requestObject.setEvent ( event );
            event.getRequests ().add ( requestObject );
            if ( event.getFrequency () > 5 ) {
                event.setStatus ( "ATAK" );
            }
            eventRepository.save ( event );

        }


        if ( event.getStatus ().equals ( "ATAK" )    ) {
            ctx.setSendZuulResponse ( false );
            // response to client
            ctx.setResponseBody ( "WYKRYTO ATAK HTTP FLOOD! " );
            ctx.getResponse ().setHeader ( "Content-Type", "text/plain;charset=UTF-8" );
            ctx.setResponseStatusCode ( HttpStatus.UNAUTHORIZED.value () );
        }
        else if (eventRepository.findAllByIpAndStatus ( request.getRemoteAddr (), "ZABLOKOWANE" ).size () > 2
        ) {
            ctx.setSendZuulResponse ( false );
            // response to client
            ctx.setResponseBody ( "WYKRYTO  ATAKI HTTP FLOOD! BRAK DOSTĘPU!" );
            ctx.getResponse ().setHeader ( "Content-Type", "text/plain;charset=UTF-8" );
            ctx.setResponseStatusCode ( HttpStatus.UNAUTHORIZED.value () );
        }

        return null;

    }
}
