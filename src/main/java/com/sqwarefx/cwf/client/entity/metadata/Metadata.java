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

import fr.lteconsulting.angular2gwt.client.JSON;
import fr.lteconsulting.angular2gwt.client.JsObject;
import fr.lteconsulting.angular2gwt.ng.core.Injectable;
import jsinterop.annotations.JsType;

@Injectable
@JsType
public class Metadata extends JsObject implements MetadataDefinitions { 

    public Metadata() {
        
        // UiUser
        set(UiUser.class.getName(), JSON.parse("{"
            + "firstName:     {businessType:'" + TEXT + "'}"
            + "lastName:      {businessType:'" + TEXT + "'}"
            + "userName:      {businessType:'" + TEXT + "'}"
            + "emailAddress:  {businessType:'" + TEXT + "', " + ATTR_REGEXP + ":'^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$'}"
            + "address:       {businessType:'" + REFERENCE + "', " + ATTR_TYPE + ":'" + UiAddress + "'}"
            + "active:        {businessType:'" + BOOLEAN + "'}}"));
        
        // UiAddress
        set(UiAddress.class.getName(), JSON.parse("{"
            + "address1:      {businessType:'" + TEXT + "'}"
            + "address2:      {businessType:'" + TEXT + "'}"
            + "city:          {businessType:'" + TEXT + "'}"
            + "zip:           {businessType:'" + TEXT + "'}"
            + "country:       {businessType:'" + TEXT + "'}}"));
    }
    
//    public static void main(String[] args) {
//        
//        Metadata m = new Metadata();
//        MetadataObject user = m.get(UiUser.class.getName());
//        JsArray<String> fields = user.getFields();
//        for (int i = 0; i < fields.length(); i++) {
//            MetadataField field = user.getField(fields.get(i));
//            String businessType = field.getBusinessType();
//            if (businessType.equals(REFERENCE)) {
//                String type = field.get(ATTR_TYPE);
//            }
//            
//        }
//        
//        
//    }
}
