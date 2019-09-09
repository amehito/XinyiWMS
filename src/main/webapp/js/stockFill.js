            var $ = function (id) {
                return "string" == typeof id ? document.getElementById(id) : id;
            };
            var Class = {
                create: function () {
                    return function () { this.initialize.apply(this, arguments); }
                }
            }
            function addEventHandler(oTarget, sEventType, fnHandler) {
                if (oTarget.addEventListener) {
                    oTarget.addEventListener(sEventType, fnHandler, false);
                } else if (oTarget.attachEvent) {
                    oTarget.attachEvent("on" + sEventType, fnHandler);
                } else {
                    oTarget["on" + sEventType] = fnHandler;
                }
            };
            function removeEventHandler(oTarget, sEventType, fnHandler) {
                if (oTarget.removeEventListener) {
                    oTarget.removeEventListener(sEventType, fnHandler, false);
                } else if (oTarget.detachEvent) {
                    oTarget.detachEvent("on" + sEventType, fnHandler);
                } else {
                    oTarget["on" + sEventType] = null;
                }
            }
            String.prototype.trim = function () {
                return this.replace(/^\s+|\s+$/g, '');
            }
            //自动完成类
            var autoComplete = Class.create();
            autoComplete.prototype = {

                initialize: function (obj, databox, data) {
                    _this = this;
                    this.input = $(obj);
                    this.data = data;
                    this.index = 0;
                    this.datalist = $(databox).getElementsByTagName('li');
                    addEventHandler(this.input, 'keyup', this.writting);
                },
                writting: function (e) {
                    var e = e || window.event;
                    var keyCode = e.keyCode || e.charCode;  //兼容ie firefox等事件

                    switch (keyCode) {

                        case 38: //上
                            _this.index--;
                            if (_this.index < 0) {
                                _this.index = $('datalist').getElementsByTagName('li').length - 1
                            }

                            if ($('datalist').getElementsByTagName('li').length > 0) {

                                for (var k = 0; k < $('datalist').getElementsByTagName('li').length; k++) {
                                    $('datalist').getElementsByTagName('li')[k].className = ''
                                }
                            }
                            $('datalist').getElementsByTagName('li')[_this.index].className = "on";
                            break;
                        case 40:
                            _this.index++;
                            if (_this.index > $('datalist').getElementsByTagName('li').length - 1) {
                                _this.index = 0;
                            }
                            if ($('datalist').getElementsByTagName('li').length > 0) {
                                for (var k = 0; k < $('datalist').getElementsByTagName('li').length; k++) {
                                    $('datalist').getElementsByTagName('li')[k].className = ''
                                }
                            }
                            $('datalist').getElementsByTagName('li')[_this.index].className = "on"
                            break;
                        //回车         
                        case 13:
                            _this.input.value = $('datalist').getElementsByTagName('li')[_this.index].innerHTML.replace(/<\/?strong>/gi, '')  //获取html，正则过滤标签，只取其值
                            $('datalist').style.display = 'none'
                            break;
                        default:
                            var temp = [];
                            var val = _this.input.value.trim();
                            if (val == '') {
                                $('datalist').style.display = 'none'
                                return;
                            }
                            //如果输入字符包含\会导致正则表达式出错，转换成\\即可  [ ] 等也需要转义
                            if (val.indexOf('\\') != -1) {
                                val = val.replace(/\\/g, '\\\\')
                            }
                            var reg = new RegExp('^(' + val + ')');
                            //利用正则把匹配到的数据 加入数组
                            for (var i = 0; i < _this.data.length; i++) {
                                //如果匹配到
                                if (reg.test(_this.data[i])) {
                                    thedata = _this.data[i].replace(reg, '<strong>$1</strong>'); //利用js正则式中replace中的占位符特性
                                    temp.push('<li title="' + i + '">' + thedata + '</li>')
                                }
                                else {
                                    $('datalist').style.display = 'none'
                                }
                            }
                            //如果匹配到数据
                            if (temp.length > 0) {
                                $('datalist').innerHTML = temp.join('');
                                $('datalist').style.display = 'block';
                                var li = $('datalist').getElementsByTagName('li');
                                li[0].className = "on";
                                _this.index = 0;
                                for (var i = 0; i < li.length; i++) {
                                    li[i].onmouseover = function () {
                                        for (var i = 0; i < li.length; i++) {
                                            li[i].className = '';
                                        }
                                        this.className = 'on';
                                        _this.index = this.getAttribute('title');
                                    }
                                    //鼠标点击
                                    li[i].onclick = function () {
                                        _this.input.value = this.innerHTML.replace(/<\/?strong>/gi, '')  //获取html，正则过滤标签，只取其值
                                        $('datalist').style.display = 'none'
                                    }
                                }
                            }
                            else {
                                $('datalist').innerHTML = '';
                            }
                            break;
                    }
                }
            }
            var data = ['yaofjaa@vip.qq.com', 'yaojaa008@163.com', 'yaojaa@yahoo.com', 'yaoj4aa@sohu.com.com', 'yaojaa@vipf.qq.com', 'yfaojaa@163.com', 'yaojfaa@sina.com', 'yaojaa@yahoo.com', 'yaojaa@sohu.com']
            var auto = new autoComplete('autoComplete', 'datalist', data)
