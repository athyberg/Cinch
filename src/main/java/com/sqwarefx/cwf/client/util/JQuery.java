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
package com.sqwarefx.cwf.client.util;

import fr.lteconsulting.angular2gwt.client.JsArray;
import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.client.interop.HTMLDocument;
import fr.lteconsulting.angular2gwt.client.interop.HTMLElement;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/**
 * 
 * JSInterop wrapper for jQuery
 * 
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "$")
public interface JQuery {

    /**
     * Attach an event handler function for one or more events to the selected elements.
     *
     * @param eventName One or more space-separated event types and optional namespaces.
     * @param function A function to execute each time the event is triggered.
     * @return jQuery element
     */
    <T> JQuery on(String eventName, Handler<Event, T> function);

    <T> JQuery off(String eventName);

    /**
     * Attach an event handler function for one or more events to the selected elements.
     *
     * @param eventName A string containing a JavaScript event type.
     * @param extraParameters Additional parameters to pass along to the event handler.
     * @return jQuery element
     */
    <T> JQuery trigger(String eventName, JsArray<T> extraParameters);
    
    JQuery draggable(Object json);
    JQuery sortable(Object json);
    JQuery resizable(Object json);
    
    JQuery parent();
    JQuery children();
    JQuery children(String selector);
    JQuery find(String selector);
    JQuery each(Object function);
    JQuery first();
    
    Object position();
    Object offset();
    
    JQuery css(String propertyName, String value);
    JQuery addClass(String className);
    JQuery removeClass(String className);
    boolean hasClass(String className);
    String attr(String key);
    Object data(String key);
    
    Number outerHeight();
    Number outerHeight(boolean includeMargin);
    Number outerWidth();
    Number outerWidth(boolean includeMargin);

    Number innerHeight();
    Number innerWidth();

//    public static int getLeftRightBorder(JQuery e) {
//      return e.outerWidth().intValue() - e.innerWidth().intValue();
//    }
    
    boolean is(String selector);
    
    Number height();
    Number width();

    JQuery height(int value);
    JQuery width(int value);

    JQuery appendTo(JQuery target);

    JQuery contextmenu(JsObject options);
    
    
    JQuery detatch(JsObject options);
    JQuery attach(JQuery tabContainer);
    
    /**
     * A function to execute when the event is triggered.
     * 
     * @param <E> the event
     * @param <T> the type of data object used
     * 
     */
    @JsFunction
    @FunctionalInterface
    public interface Handler<E, T> {
        void exec(E event, T data);
    }

    
    public static class Helper {

        /**
         * Return a collection of matched elements found in the DOM based on passed argument
         *
         * @param selector A string containing a selector expression
         * @return jQuery element of matched elements
         */
        @JsMethod(namespace = JsPackage.GLOBAL, name = "$")
        public static native JQuery $(String selector);

        /**
         * Return a collection of matched elements found in the DOM based on passed argument
         *
         * @param nativeElement
         * @return jQuery element of matched elements
         */
        @JsMethod(namespace = JsPackage.GLOBAL, name = "$")
        public static native JQuery $(HTMLElement nativeElement);

        @JsMethod(namespace = JsPackage.GLOBAL, name = "$")
        public static native JQuery $(HTMLDocument document);

        

//      public static native int getTopBottomMargin(Element pElement) /*-{
//      var e = $wnd.$(pElement);
//      return e.outerHeight(true) - e.outerHeight();
  //}-*/;
  //public static native int getLeftRightMargin(Element pElement) /*-{
//      var e = $wnd.$(pElement);
//      return e.outerWidth(true) - e.outerWidth();
  //}-*/;
  //public static native int getTopBottomBorder(Element pElement) /*-{
//      var e = $wnd.$(pElement);
//      return e.outerHeight() - e.innerHeight();
  //}-*/;
  //public static native int getLeftRightBorder(Element pElement) /*-{
//      var e = $wnd.$(pElement);
//      return e.outerWidth() - e.innerWidth();
  //}-*/;
  //public static native int getTopBottomPadding(Element pElement) /*-{
//      var e = $wnd.$(pElement);
//      return e.innerHeight() - e.height();
  //}-*/;
  //public static native int getLeftRightPadding(Element pElement) /*-{
//      var e = $wnd.$(pElement);
//      return e.innerWidth() - e.width();
  //}-*/;
  //public static native int getHeight(Element pElement, boolean pIncludeMargin) /*-{
//      var e = $wnd.$(pElement);
//      return e.outerHeight(pIncludeMargin);
  //}-*/;
  //public static native int getWidth(Element pElement, boolean pIncludeMargin) /*-{
//      var e = $wnd.$(pElement);
//      return e.outerWidth(pIncludeMargin);
  //}-*/;

    }
    
    /**
     * A wrapper for the jQuery Event object
     * 
     */
    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "$.Event")
    public static class Event {
        public String type;
    }





    
}
