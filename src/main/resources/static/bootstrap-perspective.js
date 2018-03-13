/**
The MIT License (MIT)

Copyright (c) 2016 Anders Thyberg (github.com/athyberg)

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

var $perspective, $tabContentChildListMutationObserver, $viewAttributeMutationObserver;

(function($) {
  var Perspective = function() {
    var $tabSlots;
    var $ctxMenu;
    $(window).on('resize', function() {
      $perspective.setOrientation();
      $perspective.isMobile();
      
      $('.section').each(function() {
        $perspective.viewResize($(this));
      });        
    });
  };

  Perspective.prototype.layout = function() {	  
    this.setOrientation();
    this.isMobile();
    
    // prependig 'content' to all slots
    $('.slot-single > .section').prepend('<div class="content single-content">');
    $('.slot-tab > .section').prepend('<ul class="nav nav-tabs hidden-xs"></ul><div class="content tab-content panel-group no-margin"></div>');
    $('.slot-tray > .section').prepend('<div class="content tray-content container-fluid"><div class="panel panel-default"><div class="panel-footer">');

    // setting single views to active
    $('.section').each(function() {
      var views = $(this).children('.view:not(.ignore)');
      if (views.length === 1) views.addClass('active');
    }); 

    // appending and attaching all views
    $('.slot:not(.slot-split) .content').each(function() {
        var content = $(this);
        content.siblings('.view').each(function() {
            $(this).attach(content);
        });
    });

    
    $tabContentChildListMutationObserver = new MutationObserver(function(mutations) {
        mutations.forEach(function(mutation) {
            mutation.removedNodes.forEach(function(node) {
                               
                if ($('#' + node.getAttribute('id') + '.attached').length === 0) {
                    var tab = $('li > a[href="#' + node.getAttribute('id') + '"]').parent();
                    if (tab.length === 1) {
                        // FIXME if another tab is already active don't do this next thing...
                        var next = $(tab).prev('li:not(.dropdown-tabs)');
                        if (next.length === 0) {
                            next = $(tab).next('li');
                        }
                        if (next.length === 1) {
                            next.find('a').tab('show');
                        }
                        tab.remove();
                    }            	
                }
            });
        });    
    });

    $('.tab-content').each(function() {
        $tabContentChildListMutationObserver.observe(this, { childList: true });
    });

//    $viewAttributeMutationObserver = new MutationObserver(function(mutations) {
//        mutations.forEach(function(mutation) {
//            var attr = mutation.target.attributes;
//            $('li > a[href="#' + attr.getNamedItem('id').value + '"]').text(attr.getNamedItem('name').value);
//        });    
//    });

//    $('.view:not(.ignore)').each(function() {
//        $viewAttributeMutationObserver.observe(this, { attributes: true });
//    });
    
    // tab-collapse
    $('.slot-tab > .section > ul.nav.nav-tabs')
      .prepend('<li class="dropdown dropdown-tabs hide pull-right"><a class="dropdown-toggle" data-toggle="dropdown" href="#">' + 
               '<b class="caret"></b></a><ul class="dropdown-menu"></ul></li>');
    // FIXME! move tabs that does not fit in first row to tab-collapse
    
    // tab fade transitions
    $('.slot-tab[fade]').find('.view').addClass('fade');
    $('.slot-tab[fade]').find('.view.active').addClass('in');

    
    

    // adding 'draghandles' (slot-split)
    $('.slot-split:not(.fixed) .slot:not(:first-child) > .section').after('<div class="draghandle">');
//    $('.draghandle').draggable();

    var hStart = this.handleDragStart, 
        hDrag =  this.handleDragging, 
        hStop =  this.handleDragStop;

    $('.slot-split-horizontal > .section > .slot > .draghandle').each(function(idx, element) {
      var p = $(element).parents('.slot-split-horizontal');
      $(element).draggable({
        axis: 'x',
        containment: p,
        start: hStart,
        drag:  hDrag,
        stop:  hStop
      });
    });

    $('.slot-split-vertical > .section > .slot > .draghandle').each(function(idx, element) {
      var p = $(element).parents('.slot-split-vertical');
      $(element).draggable({
        axis: 'y',
        containment: p,
        start: hStart,
        drag:  hDrag,
        stop:  hStop
      });
    });

    $tabSlots = $('.slot-tab');

    // adding the context menu
    if ($('.contextmenu').length === 0) {
      $ctxMenu = $('<div class="contextmenu"></div>');
      $ctxMenu.appendTo('.application');
      $("body").on('click', function(event) {
        $ctxMenu.trigger('menu-close', []);
        $ctxMenu.children().remove();
        $ctxMenu.removeAttr('style');
      });
    }

    // make tab containers droppable and sortable
    $tabSlots.find('.nav-tabs').droppable({
      drop: function(event, ui) {
        var view = $(ui.draggable).find('.view:not(.ignore)');
        var slotContent = $(this).siblings('.content');
        view.addClass('active');
        $(this).find('li').removeClass('active');
        slotContent.children('.view').removeClass('active');
        slotContent.children('.view').removeClass('in');
        var slot = $(this).closest('.slot.slot-tab');
        setTimeout(function() {
            $(view).trigger('view-attach', [{ perspectiveId: $('.perspective').attr('id'), slotId: slot.attr('id'), viewId: view.attr('id') }]);
        }, 100);
        
        $tabSlots.filter('.over[fade]').find('.view').addClass('fade');
        $tabSlots.filter('.over[fade]').find('.view.active').addClass('in');
      },
      tolerance: 'pointer',
      accept: '.dialog',
      activeClass: 'accepting',
      hoverClass: 'accepting-hover'
    })
    .sortable({
      delay: 500,
      axis: 'x',
      containment: 'parent',
      stop: function( event, ui ) {
        $(ui.item).css('top', '');
        $(ui.item).css('left', '');
        setTimeout(function(tabSlot) {
            var json = { layout: [], perspectiveId: $('.perspective').attr('id') };
            json.layout.push({ slotId: $(tabSlot).attr('id'), views: [] });
            $(tabSlot).find('.nav.nav-tabs li:not(.dropdown) > a').each(function() {
                json.layout[0].views.push($(this).attr('href').substring(1));
            });
            $('.perspective').trigger('layout-change', [json]);
          }, 100, $(this).closest('.slot.slot-tab'));
      }
    });
  };

  
  
  
  Perspective.prototype.attachSingle = function ( view, container ) {
      var $viewId = view.attr('id'),
          $viewTitle = view.attr('name');
      var detached = false;
      
      var view = view.panelize();
      $(view).addClass('active');
      $(view).addClass('attached');
       
      $(container).append(view);
      
      setTimeout(function(_container) {
        var json = { perspectiveId: $('.perspective').attr('id'), slotId:  $(_container).closest('.slot.slot-single').attr('id'), viewId: view.attr('id') };
        view.trigger('view-attached', [ json ]);
        if (view.hasClass('active')) {
            view.trigger('view-resize', [{ viewId: view.attr('id'), height: view.height(), width: view.width() }]);
        }
      }, 100, container);
      
      return view;
    }

  

  
  Perspective.prototype.attachTray = function ( view, container ) {
    var $viewId = view.attr('id'),
        $viewTitle = view.attr('name');
    var detached = false;
    
    var view = view.panelize();
    $(view).removeClass('active');
    $(view).addClass('footer');
    $(view).addClass('no-click');
    $(view).addClass('attached');
    $(view).find('.panel-collapse').removeClass('in');
     
    $(container).find('.panel-default > .panel-footer').append(view);
    
    setTimeout(function(_container) {
      var json = { perspectiveId: $('.perspective').attr('id'), slotId:  $(_container).closest('.slot.slot-tray').attr('id'), viewId: view.attr('id') };
      view.trigger('view-attached', [ json ]);
      if (view.hasClass('active')) {
          view.trigger('view-resize', [{ viewId: view.attr('id'), height: view.height(), width: view.width() }]);
      }
    }, 100, container);
    
    return view;
  }

  
  
  
  
  
  
  
  Perspective.prototype.viewResize = function (section) {
    // move tabs to dropdown if they don't fit in tab bar  
    var navTabs = section.parent().children('.nav-tabs');
    if (navTabs.length > 0) {
        navTabs.children('li.dropdown-tabs').first().removeClass('hide');
        var dropdownTabs = navTabs.find('li.dropdown-tabs .dropdown-menu').first();
        navTabs.append(dropdownTabs.children());
        navTabs.children('li:not(.dropdown-tabs)').each(function() {
            if (this.offsetTop > 16) {
                dropdownTabs.append(this);
            }
        });
        if (dropdownTabs.find('.active').length > 0) {
            navTabs.children('li.dropdown-tabs').first().addClass('active');
        }
        else {
            navTabs.children('li.dropdown-tabs').first().removeClass('active');
        }
        if (dropdownTabs.children().length === 0) {
            navTabs.children('li.dropdown-tabs').first().addClass('hide');
        }
    }
    section.find('.view.active').each(function() {
      var view = $(this); 
      view.trigger('view-resize', [{ viewId: view.attr('id'), height: view.height(), width: view.width() }]);
    });
  };

  Perspective.prototype.appendView = function(view) {
    $(view).appendTo('.application');
    view.focus();
  };

  Perspective.prototype.setOrientation = function() {
    $('.perspective').attr('orientation', window.innerHeight > window.innerWidth ? 'portrait' : 'landscape');
  };

  Perspective.prototype.isMobile = function() {
    var isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent);
    isMobile = isMobile || /iPhone|iPad|iPod/i.test(navigator.platform);
    isMobile = isMobile || (typeof window.orientation !== 'undefined');
    if (isMobile) {
      $(':root').attr('mobile', '');
    }
    else {
      $(':root').attr('mobile', null);
    }
    return isMobile;
  };

  Perspective.prototype.getTop = function(container, top) {
    if (typeof top !== 'undefined') {
      var ofs = $(window).height() - top - container.height(); 
      return ofs < 0 ? top + ofs : top;
    }
    return top;
  };

  Perspective.prototype.getLeft = function(container, left) {
    if (typeof left !== 'undefined') {
      var ofs = $(window).width() - left - container.width(); 
      return ofs < 0 ? left + ofs : left;
    }
    return left;
  };

  Perspective.prototype.showDialog = function(dialog, top, left) {
    dialog.css('top', 0);
    dialog.css('left', -9999);
    
    var wh = $(window).height();
    var height = dialog.height() * 100 / wh;
    if (height > 100) {
      height = wh * 0.85;
      dialog.css('height', height);
    }

    setTimeout(function() {
        dialog.css('top', $perspective.getTop(dialog, top) || ($(window).height() - dialog.height()) / 2);
        dialog.css('left', $perspective.getLeft(dialog, left) || ($(window).width() - dialog.width()) / 2);
    }, 100);
    
    dialog.addClass('raised');
    dialog.focus();
  };

  Perspective.prototype.attachTab = function ( view, container ) {
    var $active = view.hasClass('active') ? 'active' : '',
        $viewId = view.attr('id'),
        $viewTitle = view.attr('name');
    var detached = false;
    var tab = $('<li class="' + $active + '"><a data-toggle="tab" href="#' + $viewId + '">' + $viewTitle + '</a></li>');
    $(container).siblings('.nav-tabs').append(tab);
    
    view = view.panelize();
    $(container).append(view);
    view.addClass('tab-pane');
    view.addClass('attached');
    
    $(view).find('.panel-default').on('shown.bs.collapse', function (e) {
        view.trigger('view-active', [{ viewId: view.attr('id') }]);
        view.trigger('view-resize', [{ viewId: view.attr('id'), height: $(window).innerHeight(), width: $(window).width() }]);
    })
    
//    var panel = '<div class="panel panel-default no-margin no-background' + ($active ? ' in' : '') + ' fit"><div class="panel-heading visible-xs"><h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse-' + $(view).attr('id') + '">'
//        + $(view).attr('name')
//        + '</a></h4></div><div id="collapse-' + $(view).attr('id') + '" class="panel-collapse collapse in' + ($active ? ' in' : '') + '"><div class="panel-body no-padding no-border">';
//    $(view).prepend(panel);
//    $(view).children(':not(.panel)').appendTo($(view).find('.panel-default .panel-body'));
    
    tab.on('shown.bs.tab', function (event) {
      view.trigger('view-active', [{ viewId: view.attr('id') }]);
      view.trigger('view-resize', [{ viewId: view.attr('id'), height: view.height(), width: view.width() }]);
    });
        
    setTimeout(function(_container) {
      var json = { perspectiveId: $('.perspective').attr('id'), slotId:  $(_container).closest('.slot.slot-tab').attr('id'), viewId: view.attr('id') };
      view.trigger('view-attached', [ json ]);
      if (view.hasClass('active')) {
          view.trigger('view-resize', [{ viewId: view.attr('id'), height: view.height(), width: view.width() }]);
      }
    }, 100, container);
    
    tab.on('contextmenu', function(event) {
      // no context menus on mobile devices
      if ($perspective.isMobile()) {
        return;
      }
      event.preventDefault();
      var c = $('<button type="button" class="btn btn-danger btn-lg" aria-label="Close"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>');
      var d = $('<button type="button" class="btn btn-default btn-lg" aria-label="Detach"><span class="glyphicon glyphicon-new-window" aria-hidden="true"></span></button>');
      $ctxMenu.children().remove();
      $ctxMenu.append(d).append(c);

      var t = $perspective.getTop($ctxMenu, event.pageY);
      var l = $perspective.getLeft($ctxMenu, event.pageX);

      $ctxMenu.css({ top: t + 'px', left: l + 'px', display: 'inline-block' });
      $perspective.appendView($ctxMenu);

      // NOTE! It is up to the 'view' to decide what should happen to the tab when it is closed or detached
      var topOfs = $('.navbar').outerHeight();
      c.on('click', function(event) {
        view.trigger('view-close', [{ viewId: view.attr('id'), top: (t - topOfs), left: event.pageX }]);
      });
      d.on('click', function(event) {
        view.trigger('view-detach', [{ slotId: tab.closest('.slot.slot-tab').attr('id'), viewId: view.attr('id'), top: (t - topOfs), left: l, height: view.height(), width: view.width() }]);
      });
      
      $("body").on('click', function(event) {
        $ctxMenu.children().remove();
        $ctxMenu.removeAttr('style');
//        $("body").off('click');
      });
    });

    tab.type = 'tab';
    return tab;
  }

  // -------------------------------------------------------------------
  // HANDLE SLOT SPLIT DRAG AND RESIZE
  // -------------------------------------------------------------------

  var dragStartX, dragStartY;

  Perspective.prototype.handleDragStart = function ( event, ui ) {
    dragStartX = parseInt( ui.offset.left );
    dragStartY = parseInt( ui.offset.top );  
  };

  Perspective.prototype.handleDragging = function ( event, ui) {
    // nothing yet  
  };

  Perspective.prototype.handleDragStop = function ( event, ui ) {
    var section = $(ui.helper).parent().parent(); 
    var slots_dl = section.children('.slot'); 
    var slot_resizing = slots_dl.has($(ui.helper));
    var slot_following = slot_resizing.prev();
    var vertical = (parseInt( ui.offset.left ) - dragStartX === 0);
    var f, r, uiOfs, parentOfs, uiHelperOuter, parentOuter;

    if (vertical) {
      uiHelperOuter = ui.helper.outerHeight();
      parentOuter = section.outerHeight();
      uiOfs = ui.offset.top;
      parentOfs = section.offset().top;
    }
    else {
      uiHelperOuter = ui.helper.outerWidth();
      parentOuter = section.outerWidth();
      uiOfs = ui.offset.left;
      parentOfs = section.offset().left;
    }

    var totalPrev = 0;
    var totalNext = 0;
    slots_dl.each(function() {
      var slot = $(this), slotOuter;
      if (vertical) {
        slotOuter = slot.outerHeight();
      }
      else {
        slotOuter = slot.outerWidth();
      }

      if (!slot.is(slot_resizing) && !slot.is(slot_following) && slot.nextAll().is(slot_following)) {
        totalPrev += slotOuter;
      }
      else if (!slot.is(slot_resizing) && !slot.is(slot_following)) {
        totalNext += slotOuter;
      }
      slot.css('flex', "0 0 " + (Math.round(slotOuter * 100000 / parentOuter) / 1000) + "%");
    });

    f = parseInt( uiOfs ) - parentOfs + uiHelperOuter/2 - totalPrev;
    r = Math.round((parentOuter - f - totalPrev - totalNext) * 100000 / parentOuter) / 1000;
    f = Math.round(f * 100000 / parentOuter) / 1000;
    
    $(ui.helper).css('top', '');
    $(ui.helper).css('left', '');
    slot_resizing.css('flex', "0 0 " + r + "%");
    slot_following.css('flex', "0 0 " + f + "%");

    section.find('.section > .content').each(function() { 
      $perspective.viewResize($(this));
    });
    
    var slotIds = [];
    section.children('.slot[style*="flex"]').each(function() { 
      slotIds.push('#' + $(this).attr('id'));
    });

    setTimeout(function(slotIds) {
      var json = { layout: [], perspectiveId: $('.perspective').attr('id') };
        for (var i = 0; i < slotIds.length; i++) {
          json.layout.push({ slotId: $(slotIds[i]).attr('id'), style: $(slotIds[i]).attr('style') });
        }
      $('.perspective').trigger('layout-change', [json]);
    }, 100, slotIds);
    
    $ctxMenu.trigger('menu-close', []);
  };

  // -------------------------------------------------------------------
  // UTILS / PLUGINS
  // -------------------------------------------------------------------

  var $zIndex = 1;
  
  $.fn.contextmenu = function(options) {
    $ctxMenu.children().remove();
    if (typeof options === 'undefined' || options === null) {
        return;
    }
    
    var widget = $(this);
    
    var ul = $("<ul class='context-menu dropdown-menu'>");
    $ctxMenu.append(ul);
    var optIdx = 0;
    for (var i = 0; i < options.options.length; i++) {
        var opt = options.options[i];
        if (typeof opt === 'undefined' || opt === null) {
            ul.append("<li class='divider'></li>");
        }
        else {
            ul.append("<li data-index='" + (optIdx++) + "'><a class='menuitem'>" + opt + "</a></li>");
        }
    }  

    ul.find("li[data-index]").each(function() {
        var li = $(this);
        li.on('click', function(event) {
            var t = $(this).children().contents().filter(function() { return this.nodeType === 3 });
            if ($.isFunction(options.select)) {
                options.select('option-select', [{ index: li.data('index'), text: t[0].nodeValue }]);
                $ctxMenu.trigger('menu-close', []);
            }
        });
    });
    
    $ctxMenu.css({left: "-9999px", top: 0, display: "block"})
    $ctxMenu.css({
        left: ($(widget).offset().left - ul.outerWidth() + $(widget).outerWidth()) + "px",
        top: ($(widget).offset().top + $(widget).outerHeight()) + "px",
        zIndex: $zIndex++
    });
    
    return $ctxMenu;
  };
  
  $.fn.panelize = function() {
    var view = $(this);
    if (view.hasClass('ignore')) {
        return view;
    }
    var $active = view.hasClass('active') ? 'active' : '',
        $viewId = view.attr('id'),
        $viewTitle = view.attr('name');
    var panel = '<div class="panel panel-default no-margin" fit><div class="panel-heading"><h4 class="dialog-title"><a data-toggle="collapse" href="#collapse-' 
        + $(view).attr('id') + '">' + $(view).attr('name') + '</a></h4></div><div id="collapse-' + $(view).attr('id') 
        + '" class="panel-collapse collapse in"><div class="panel-body">';
    $(view).prepend(panel);
    $(view).children(':not(.panel)').appendTo($(view).find('.panel-default .panel-body'));
    return view;
  };
  
  // TODO detach should not be a jQuery plugin
  $.fn.detatch = function(options) {
    var view = $(this);
    view.addClass('active');
    view.removeClass('attached');
    var panel = view.closest('.dialog');
    panel.addClass('fade in');
    
    view = view.panelize();
    
    var heading = $(view).find('.panel-heading');
//      '<div class="panel-heading">' +
    heading.prepend('<button type="button" class="minimize" data-minimize="' + panel.attr('id') + '" aria-label="Minimize"><span aria-hidden="true">&#45;</span></button>');
    heading.prepend('<button type="button" class="close" data-dismiss="' + panel.attr('id') + '" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
//      '<h4 class="dialog-title" href="#' + view.attr('id') + '">' + view.attr('name') + '</h4></div>');
//    
//    var body = $('<div class="panel panel-default"><div class="panel-body"></div></div>');
//    body.prepend(heading);
//    body.find('.panel-body').append(view);
//    
//    
//    panel.append(body);  
    
    // close (default function for dialog)
    
    $(heading).find('.close').on('mousedown', function(event) {
      view.trigger('view-close', [{ viewId: view.attr('id'), top: event.pageY, left: event.pageX }]);
    });

    // minimize (default function for dialog)
    $(heading).find('.minimize').on('mousedown', function(event) {
      view.trigger('view-minimize', [{ viewId: view.attr('id'), top: event.pageY, left: event.pageX }]);
    });

    // focus
    panel.on('mousedown', function() {
      panel.css('zIndex', $zIndex++);
      view.trigger('view-active', [{ viewId: view.attr('id') }]);      
    });
    
    panel.draggable({
      start: function() {
      },
      opacity: 0.75,
      handle: '.panel-heading',
      containment: $('.perspective')
    });

    panel.resizable({
      handles: "n, e, s, w",
      resize: function(event, ui) {
        event.stopPropagation();
        var h_pad = 0; //view.outerWidth(true) - view.width();
        var v_pad = 0; //view.outerHeight(true) - view.height();
        var headerHeight = 0; //panel.find('.panel-heading').outerHeight();
        
        ui.size.height = Math.max(ui.size.height, 180);
        ui.size.width = Math.max(ui.size.width, 250);
        view.trigger('view-resize', [{ viewId: view.attr('id'), height: ui.size.height - v_pad - headerHeight, width: ui.size.width - h_pad }]);
      }
    });
    
    panel.type = 'dialog';

    $perspective.showDialog(panel, Number(options.top), Number(options.left));

    view.trigger('view-resize', [{ viewId: view.attr('id'), height: options.height, width: options.width }]);
    return panel;
  };

  // TODO attach should not be a jQuery plugin
  $.fn.attach = function(container) {
    var view = $(this);
    if (view.hasClass('ignore')) {
        view.appendTo(container);
        return null;
    }
    if (container.hasClass('single-content')) {
        return $perspective.attachSingle($(this), container);
    }
    else if (container.hasClass('tab-content')) {
        return $perspective.attachTab($(this), container);
    }
    else if (container.hasClass('tray-content')) {
        return $perspective.attachTray($(this), container);
    }
  };

  $perspective = new Perspective();
  $.ui.resizable.prototype.widgetEventPrefix = 'view-';

}) (window.jQuery);
