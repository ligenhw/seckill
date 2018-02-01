console.log('seckill.js');
var seckill = {
    URL: {
        time: '/seckill/time/now',
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        excution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    validatePhone: function (phone) {
        if (phone && phone.length === 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    handlerSeckill: function (seckillId, node) {
        node.hide()
            .html('<button class="btn btn-primary btn-lg" id="killBtn">kill begin</button>');
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            if (result && result.success) {
                var exposer = result.data;
                if (exposer.exposed) {
                    var md5 = exposer.md5;
                    var killUrl = seckill.URL.excution(seckillId, md5);
                    console.log('killUrl=${killUrl}');
                    $('#killBtn').one('click', function (event) {
                        $(this).addClass('disabled');
                        $.post(killUrl, {}, function (result) {
                            if (result) {
                                var killResult = result.data;
                                var state = killResult.state;
                                var stateInfo = killResult.stateInfo;
                                if (result.success) {
                                    node.html('<span class="label label-success">'
                                        + stateInfo + '</span>');
                                } else {
                                    node.html('<span class="label label-danger">'
                                        + stateInfo + '</span>');
                                }
                                console.log('seckill result state=${state}');
                            }
                        })
                    })
                    node.show();
                } else {
                    var now = exposer.now;
                    var start = exposer.startTime;
                    var end = exposer.endTime;
                    seckill.countDown(seckillId, now, start, end);
                }
            }
        });
    },
    countDown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        if (nowTime > endTime) {
            seckillBox.html('kill end');
        } else if (nowTime < startTime) {
            var killTime = new Date(startTime);
            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime('kill counting : %D day %H hour %M min %S sencond');
                seckillBox.html(format);
            }).on('finish.countdown', function () {
                seckill.handlerSeckill(seckillId, seckillBox);
            });
        } else {
            seckill.handlerSeckill(seckillId, seckillBox);
        }
    },
    detail: {
        init: function (params) {
            var killPhone = $.cookie('killPhone');
            var startTime = params.startTime;
            var endTime = params.endTime;
            var seckillId = params.seckillId;
            if (!seckill.validatePhone(killPhone)) {
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false,
                });
                $('#killPhoneBtn').click(function () {
                   var inputPhone = $('#killPhoneKey').val();
                   if (seckill.validatePhone(inputPhone)) {
                       $.cookie('killPhone', inputPhone, {expires:7, path:'/seckill'});
                       window.location.reload();
                   } else {
                       $('#killPhoneMessage').hide().html(
                           '<label class="label label-danger">phone number error</label>')
                           .show(300);
                   }
                });
            }

            $.get(seckill.URL.time, {}, function (result) {
                if (result && result.success) {
                    var nowTime = result.data;
                    seckill.countDown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('time result=${}', result);
                }
            });
        }
    },
}