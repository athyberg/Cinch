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
package com.sqwarefx.cwf.client.entity.metadata;

import com.sqwarefx.cwf.client.entity.UiAddress;
import com.sqwarefx.cwf.client.entity.UiUser;

public interface MetadataDefinitions {

    // Business types
    String BOOLEAN = "boolean";
    String DATE = "date";
    String NUMBER = "number";
    String PASSWORD = "password";
    String REFERENCE = "reference";
    String TEXT = "text";
    
    // Attributes
    String ATTR_MAX = "max";
    String ATTR_MIN = "min";
    String ATTR_NOT_NULL = "notNull";
    String ATTR_REGEXP = "regexp";
    String ATTR_TYPE = "type";
    
    // Object types
    String UiAddress = UiAddress.class.getName();
    String UiUser = UiUser.class.getName();
    
}
