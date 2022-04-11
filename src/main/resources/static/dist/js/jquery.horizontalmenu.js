(function ($) {
    $.fn.horizontalmenu = function (option) {
        var setting = {
            itemClick: function(sender) {
                return true;
            }
        };

        if (option) $.extend(setting, option);

        var isOverflown = function(selector) {
            var element = $(selector)[0];
            return element.scrollHeight > element.clientHeight || element.scrollWidth > element.clientWidth;
        }

        var adaptiveTab = function(tabWrapper) {
                var tab = $(tabWrapper).find('.ah-tab');
                var tabOvorflowList = $(tabWrapper).find('.ah-tab-overflow-wrapper');
                var isOver = isOverflown(tab);
                if(isOver) {
                    tab.addClass('ah-tab-overflow-right');
                } else {
                    tab.removeClass('ah-tab-overflow-right');
                }
                tabOvorflowList.attr('data-ah-tab-active', isOver);
                var items =  tab.find('.ah-tab-item');
                var activeItem = tab.find('.ah-tab-item[data-ah-tab-active="true"]');
            
                var activeIndex = activeItem.index();
                var marginLeft = 0,
                    marginRight = 0;
                    
                if(isOver) {
                   // console.clear()
                    for(var i = 0; i < items.length; i++) {
                        var val = items.eq(i).width() + parseInt(items.eq(i).css('margin-right'));
                        if(i < activeIndex) {
                            marginLeft += val;    
                            continue;
                        }
                        marginRight += val;
                    }
                    console.log(marginRight);
                    if(marginLeft + activeItem.width() + 80 > $(tab).width()) {
                        marginLeft *= -1;
                        if(activeIndex) {
                            
            
                                var  dW =  $(tab).width() - marginRight - 80;
                                marginLeft += parseInt(activeItem.css('margin-right'));
                                console.log(dW);
                                if(dW > 0)
                                    marginLeft += dW ;
                            //вычислить ширину после выделенного элемента назовем его dA. dW = если tab.width() - dA > 0, то marginLeft -= dW 
                            tab.addClass('ah-tab-overflow-left');
                        }
                    } else {
                        marginLeft = 0;
                        tab.removeClass('ah-tab-overflow-left');
                    }
                } else {
                    tab.removeClass('ah-tab-overflow-left');
                }
                items.css('transform', 'translateX(' + marginLeft + 'px)');
            }

            var initialize = function(wrapper) {
                if(wrapper.find('.ah-tab-overflow-wrapper').length) return false;

                var items = wrapper.find('.ah-tab-item');
                items.bind('click', function() { 
                    var isContinue = setting.itemClick($(this));
                    if(!isContinue) {
                        var index =  $(this).index();
                        var w = $(this).closest('.ah-tab-wrapper');
                        w.find('.ah-tab-item').removeAttr('data-ah-tab-active');
                        w.find('.ah-tab .ah-tab-item').eq(index).attr('data-ah-tab-active', 'true');
                        w.find('.ah-tab-overflow-wrapper .ah-tab-item').eq(index).attr('data-ah-tab-active', 'true');
                        adaptiveTab(w);
                    }
                    return isContinue;
                });

                $('<div>', {
                    class: 'ah-tab-overflow-wrapper',
                    append: $('<button>', {
                        type: 'menu',
                        class: 'ah-tab-overflow-menu'
                    }).add($('<div>', {
                        class: 'ah-tab-overflow-list',
                        append: items.clone(true, true).removeAttr('style')
                    })) 
                }).appendTo(wrapper);

                adaptiveTab(wrapper);

                var resizeStabilizer = undefined;
                $(window).bind('resize', function () {
                    if (resizeStabilizer) clearTimeout(resizeStabilizer);
                    resizeStabilizer = setTimeout(function () {
                       adaptiveTab(wrapper);
                    }, 20);
                });
            }

        return this.each(function () {
            initialize($(this));
        });
    };
})(jQuery);