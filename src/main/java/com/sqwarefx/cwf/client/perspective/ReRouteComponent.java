/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2017 Sqwarefx AB.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.sqwarefx.cwf.client.perspective;

import fr.lteconsulting.angular2gwt.client.JsArray;
import fr.lteconsulting.angular2gwt.client.interop.ng.core.OnInit;
import fr.lteconsulting.angular2gwt.client.interop.ng.router.ActivatedRoute;
import fr.lteconsulting.angular2gwt.client.interop.ng.router.Router;
import fr.lteconsulting.angular2gwt.ng.core.Component;
import jsinterop.annotations.JsType;

/**
 *
 * Dummy component
 * 
 */
@Component( selector = "cwf-re-route", template = "<!-- re-routing... -->" )

@JsType
public class ReRouteComponent implements OnInit {

    public ActivatedRoute route;
    public Router router;

    public ReRouteComponent(ActivatedRoute route, Router router) {
        this.route = route;
        this.router = router;
    }
    
    @Override
    public void ngOnInit() {
        route.params.forEach( params -> {
            String value = params.get( "perspectiveId" );
            if( value != null ) {
                router.navigate(JsArray.of("perspective", value));
            }
        } );
    }

}