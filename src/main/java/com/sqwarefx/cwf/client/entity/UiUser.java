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
package com.sqwarefx.cwf.client.entity;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Object" )
public class UiUser extends UiEntity {

    @JsProperty
    public native String getFirstName();

    @JsProperty
    public native void setFirstName(String firstName);

    @JsProperty
    public native String getLastName();
    
    @JsProperty
    public native void setLastName(String lastName);

    @JsProperty
    public native String getUserName();
    
    @JsProperty
    public native void setUserName(String userName);

    @JsProperty
    public native String getEmailAddress();
    
    @JsProperty
    public native void setEmailAddress(String emailAddress);

    @JsProperty
    public native UiAddress getAddress();
    
    @JsProperty
    public native void setAddress(UiAddress emailAddress);
    
    @JsProperty
    public native boolean isActive();
    
    @JsProperty
    public native void setActive(boolean active);
    
}
