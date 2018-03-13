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
package com.sqwarefx.server.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sqwarefx.cwf.client.entity.event.PerspectiveLayoutChangeEvent;
import com.sqwarefx.server.entity.CustomViewModel;
import com.sqwarefx.server.entity.MenuItem;
import com.sqwarefx.server.entity.Perspective;
import com.sqwarefx.server.entity.PerspectiveSlot;
import com.sqwarefx.server.entity.SlotType;
import com.sqwarefx.server.entity.TableViewModel;
import com.sqwarefx.server.entity.TreeViewModel;
import com.sqwarefx.server.entity.ViewModel;


public class MockDB implements Database, SlotType {

    protected static final Map<String, Perspective> perspectives = new HashMap<>();
    protected static final Map<String, Map<String, PerspectiveSlot>> slots = new HashMap<>();
    protected static final Map<String, Map<String, ViewModel>> views = new HashMap<>();

    protected MockDB() {
        initDB();
    }

    @Override
    public Collection<Perspective> getPerspectives() {
        return Arrays.asList(REFERENCEDATA, DASHBOARD);
    }
    
    @Override
    public Perspective getPerspective(String perspectiveId) {
        return perspectives.get(perspectiveId);
    }
    
//    @SuppressWarnings("unchecked")
    @Override
    public Perspective updatePerspective(String perspectiveId, PerspectiveLayoutChangeEvent layoutChange) {
//        assert layoutChange.perspectiveId.equals(perspectiveId);
//        
//        Perspective perspective = perspectives.get(perspectiveId);
//        assert perspective != null;
//        Map<String, PerspectiveSlot> perspectiveSlots = slots.get(perspectiveId);
//        assert perspectiveSlots != null;
//        
//        System.out.println();
//        System.out.println("updatePerspective: " + perspectiveId);
//        for (Object layout : layoutChange.layout) {
//            if (layout instanceof Map) {
//                String slotId = ((Map<String, String>) layout).get("slotId");
//                PerspectiveSlot slot = perspectiveSlots.get(slotId);
//
//                assert slot != null;
//                
//                String style = ((Map<String, String>) layout).get("style");
//                if (style != null) {
//                    System.out.println("    " + slotId + " -> " + style);
//                    slot.setStyle(style);
//                }
//                ArrayList<String> viewIds = ((Map<String, ArrayList<String>>) layout).get("views");
//                if (viewIds != null) {
//                    List<ViewModel> sortedViews = new ArrayList<>();
//                    System.out.println("    " + slotId + ": " + viewIds);
//                    Map<String, ViewModel> viewMap = views.get(perspectiveId);
//                    for (String viewId : viewIds) {
//                        sortedViews.add(viewMap.get(viewId));
//                    }
//                    slot.viewModels = sortedViews.toArray(new ViewModel[sortedViews.size()]);
//                }
//            }
//        }
//        return perspective;
        return null;
    }
    
    @Override
    public Collection<MenuItem> getAvailablePerspectives() {
        List<MenuItem> available = new ArrayList<>();
        for (Perspective perspective : getPerspectives()) {
            available.add(new MenuItem(perspective.perspectiveId, perspective.perspectiveId));
        }
        return available;
    }

    
    // -------------------
    
    public void initDB() {
        mapify(REFERENCEDATA);
        mapify(DASHBOARD);
    }
    
    private static final Perspective REFERENCEDATA = 
        new Perspective("referenceData").setRoot(
            new PerspectiveSlot(splitVertical, "fixed")
            .addChild(new PerspectiveSlot(splitHorizontal)
                .addChild(new PerspectiveSlot(single, "30%")
                    .addView(new TreeViewModel("navigation", true, true)))
                .addChild(new PerspectiveSlot(splitVertical)
                    .addChild(new PerspectiveSlot(tab)
                        .addView(new TableViewModel("business", true, true))
                        .addView(new CustomViewModel("hackish", false, true))
                        .addView(new TableViewModel("rules", false, true)))
                    .addChild(new PerspectiveSlot(tab, "40%")
                        .addView(new TableViewModel("sessions", true, true))
                        .addView(new TableViewModel("logs", false, true)))))
            .addChild(new PerspectiveSlot(tray, "70px", "footer")
                .addView(new CustomViewModel("footer", true, true))));

    private static final Perspective DASHBOARD = 
        new Perspective("dashboard").setRoot(
            new PerspectiveSlot(splitVertical, "fixed")
            .addChild(new PerspectiveSlot(splitHorizontal, "fixed")
                .addChild(new PerspectiveSlot(splitVertical, "30% fixed")
                    .addChild(new PerspectiveSlot(single, "50%")
                        .addView(new CustomViewModel("price-performance", true, true)))
                    .addChild(new PerspectiveSlot(single, "25%")
                        .addView(new CustomViewModel("volume", true, true)))
                    .addChild(new PerspectiveSlot(single, "25%")
                        .addView(new CustomViewModel("ownership", true, true))))
                    
                .addChild(new PerspectiveSlot(splitVertical, "20% fixed")
                    .addChild(new PerspectiveSlot(single, "25%")
                        .addView(new CustomViewModel("price-action", true, true)))
                    .addChild(new PerspectiveSlot(single, "25%")
                        .addView(new CustomViewModel("director-dealings", true, true)))
                    .addChild(new PerspectiveSlot(single, "25%")
                        .addView(new CustomViewModel("valuation", true, true)))
                    .addChild(new PerspectiveSlot(single, "25%")
                        .addView(new CustomViewModel("correlation", true, true))))

                .addChild(new PerspectiveSlot(splitVertical, "30% fixed")
                    .addChild(new PerspectiveSlot(single, "25%")
                        .addView(new CustomViewModel("eps-momentum", true, true)))
                    .addChild(new PerspectiveSlot(single, "25%")
                        .addView(new CustomViewModel("implied-volatility", true, true)))
                    .addChild(new PerspectiveSlot(single, "25%")
                        .addView(new CustomViewModel("short-interest", true, true)))
                    .addChild(new PerspectiveSlot(single, "25%")
                        .addView(new CustomViewModel("cds", true, true))))

                .addChild(new PerspectiveSlot(splitVertical, "20% fixed")
                    .addChild(new PerspectiveSlot(single, "75%")
                        .addView(new CustomViewModel("news", true, true)))
                    .addChild(new PerspectiveSlot(single, "25%")
                        .addView(new CustomViewModel("events", true, true)))))
            
            .addChild(new PerspectiveSlot(tray, "70px", "footer")
                .addView(new CustomViewModel("footer", true, true))));
    
    public static class Singelton {
        
        private static final MockDB instance = new MockDB();
        
        public static MockDB get() {
            return instance;
        }
    }

    
    
    private void mapify(Perspective perspective) {
        perspectives.put(perspective.perspectiveId, perspective);
        mapify(perspective, perspective.root);
    }

    private void mapify(final Perspective perspective, PerspectiveSlot slot) {
        Map<String, PerspectiveSlot> map = slots.get(perspective.perspectiveId);
        if (map == null) {
            slots.put(perspective.perspectiveId, map = new HashMap<>());
        }
        map.put(slot.slotId, slot);
        for (PerspectiveSlot child : slot.children) {
            mapify(perspective, child);
        }
        Map<String, ViewModel> viewMap = views.get(perspective.perspectiveId);
        if (viewMap == null) {
            views.put(perspective.perspectiveId, viewMap = new HashMap<>());
        }
        for (ViewModel view : slot.viewModels) {
            viewMap.put(view.id, view);
        }
    }
    
}
