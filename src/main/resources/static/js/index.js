/* 鼠标点击文字特效 */
var font = new Array("富强", "民主", "文明", "和谐", "自由", "平等", "公正", "法治", "爱国", "敬业", "诚信", "友善");
var color = new Array('#ff0000', '#eb4310', '#fbb417', '#ffff00', '#cdd541', '#99cc33', '#3f9337',
    '#219167', '#24998d', '#1f9baa', '#0080ff', '#3366cc', '#333399', '#003366', '#800080', '#a1488e',
    '#c71585', '#bd2158', '#eec142', );
var f_idx = Math.floor(Math.random() * 20) % font.length;
jQuery(document).ready(function($) {
    $("body").click(function(e) {
        var $i = $("<span />").text(font[f_idx]);
        f_idx = Math.floor(Math.random() * 20) % font.length;
        c_idx = Math.floor(Math.random() * 40) % color.length;
        var x = e.pageX,
            y = e.pageY;
        $i.css({
            "z-index": 9999999999999999999999999,
            "top": y - 20,
            "left": x,
            "position": "absolute",
            "font-weight": "bold",
            "color": color[c_idx]
        });
        $("body").append($i);
        $i.animate({
                "top": y - 180,
                "opacity": 0
            },
            1500,
            function() {
                $i.remove();
            });
    });
});
var percentScroll = 0;
$(document).bind('mousewheel DOMMouseScroll', function(event) { //on也可以 bind监听
    //获取滚动条的页面百分比
    window.onscroll = function() {
        //htmlHeight 是网页的总高度
        var htmlHeight = document.documentElement.scrollHeight;
        //clientHeight是网页在浏览器中的可视高度，
        var clientHeight = document.documentElement.clientHeight;
        //scrollTop是浏览器滚动条的top位置，
        var scrollTop = document.documentElement.scrollTop;
        //通过判断滚动条的top位置与可视网页之和与整个网页的高度来返回各项参数
        percentScroll = (scrollTop / (htmlHeight - clientHeight))*100; //该值为滚动条的页面百分比
        if (percentScroll<=100){
            $('#example4')
                .progress({
                    percent: percentScroll
                });
        }
    }
})
$(document).on("click", "#stackable-hide", function() {
    $(".h-item").toggleClass('h-moblile-hide');
})