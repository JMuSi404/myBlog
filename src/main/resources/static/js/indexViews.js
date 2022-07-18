$(function () {
    $("#indexBackground").attr("style","margin-top: 60px;background: url('/img/b"+Math.round(Math.random()*(6-1)+1)+".jpg')  center center / cover no-repeat;")
    RandomlyGetFamousQuotes();
    BlogsShowList();
    TagShowList();
    queryCount();
})

    $(document).on("click","#scroll_down_btn",function () {
    $(window).scrollTo($("#Indexbody"),1000);
})

    $(document).on("click","#backToTheTop",function () {
    $(window).scrollTo(0,500);
})

    var waypoint = new Waypoint({
    element: document.getElementById('Indexbody'),
    handler: function(direction) {
    if (direction=='down'){
    $("#backToTheTop").removeClass("animate__animated animate__bounceOutRight");
    $("#backToTheTop").addClass("animate__animated animate__bounceInRight");
    $("#backToTheTop").show();
}else {
    $("#backToTheTop").hide();
    $("#backToTheTop").removeClass("animate__animated animate__bounceInRight");
    $("#backToTheTop").addClass("animate__animated animate__bounceOutRight");
    $("#backToTheTop").show();
}
}
})

    function RandomlyGetFamousQuotes() {
    $.ajax({
        url:"https://v1.hitokoto.cn/",
        data:{
            Cati:"d",
            max_length:"20"
        },
        type:"GET",
        success:function (data){
            $("#p-title").text(data.hitokoto);
        }
    })}

    function BlogsShowList(pageNum,pageSize) {
    $.ajax({
        url:"/BlogsShowList",
        type:"GET",
        data: {
            "pageNum":pageNum,
            "pageSize":pageSize
        },
        success:function (data) {
            $("#contentList").empty();
            $.each(data.list, function (index, item) {
                $("<div class=\"ui attached animate__animated animate__fadeInUp\"></div>").append(
                    $("<div class=\"d-web-info-box\" data-wow-duration=\"0.4s\"></div>").append(
                        $("<div class=\"ui mobile stackable grid\"></div>").append(
                            // 博客图片
                            $("<div class=\"seven wide column\" style='overflow: hidden;'></div>").append(
                                $("<img  class=\"ui rounded image centered Itemimg\"/>")
                                    .attr("src", item.firstPicture)
                            )
                        ).append(
                            //博客展示内容
                            $("<div class=\"nine wide column h-padded-td-large\"></div>").append(
                                $("<a ></a>").attr("href", "javascript:;").append(
                                    $("<h3 class=\"ui header titleSuspension\"></h3>").append(item.title)
                                ).on("click", function () {
                                    window.location.href = "/toBlog/" + item.id;
                                })
                            ).append(
                                $("<p class=\"m-text\"></p>").append(item.description + "......")
                            ).append(
                                $("<div class=\"ui grid\"></div>").append(
                                    $("<div class=\"wide column\"></div>").append(
                                        $("<div class=\"ui mini horizontal link list\"></div>").append(
                                            $("<div class=\"item\"></div>").append(
                                                $("<div class=\"content\"></div>").append(
                                                    // 博客创建者
                                                    $("<span class=\"header\"></span>").append(
                                                        $("<i class = \"user outline red  icon\" ></i>")
                                                    ).append(item.userName)
                                                )
                                            )
                                        ).append(
                                            //创建时间
                                            $("<div class=\"item\"></div>").append(
                                                $("<i class=\"clock outline blue icon\"></i>")
                                            ).append(item.edittime != null && item.edittime != '' ? " " + item.edittime : " " + item.createtime)
                                        ).append(
                                            // 浏览量
                                            $("<div class=\"item\"></div>").append(
                                                $("<i class=\"eye green icon\"></i>")
                                            ).append(" " + item.views)
                                        ).append(
                                            // 评论量
                                            $("<div class=\"item\"></div>").append(
                                                $("<i class=\"comment alternate outline orange icon\"></i>")
                                            ).append(" " + item.commentCount)
                                        )
                                    )
                                )
                            )
                        )
                    )
                ).appendTo("#contentList");
            })

            $("#blogPaging").empty();
            if (data.navigatepageNums.length > 1) {
                let div = $("<div class=\"ui pagination menu\"></div>");
                div.append(
                    $("<a class=\"item\">上一页&nbsp;&nbsp;&nbsp;</a>").on("click", function () {
                        $(window).scrollTo($("#scroll_down_btn"));
                            BlogsShowList(data.prePage, null);
                    })
                )
                $.each(data.navigatepageNums, function (index, item) {
                    div.append(
                        $("<a class=\"item \"></a>").append(item).addClass(item == data.pageNum ? "active" : "").on("click", function () {
                            $(window).scrollTo($("#scroll_down_btn"));
                            BlogsShowList(item, null);
                        })
                    )
                })
                div.append(
                    $("<a class=\"item\">&nbsp;&nbsp;&nbsp;下一页</a>").on("click", function () {
                        $(window).scrollTo($("#scroll_down_btn"));
                        BlogsShowList(data.nextPage, null);
                    })
                )
                div.appendTo("#blogPaging");
            }
        }
    })
}
    function TagShowList() {
    $.ajax({
        url:"/TagShowList",
        type:"GET",
        success:function (data){
            $.each(data,function (index,item) {
                var color = new Array("orange", "teal", "blue","grey","red");
                var random= Math.floor(Math.random() * 10) % color.length;
                $("<a href=\"javascript:;\" class=\"ui left label  m-margin-tb-mid\"></a>").addClass(color[random]).append(
                    $("<span></span>").append(item.name)
                ).on("click",function () {
                    window.location.href="/toTags?TagId="+item.id;
                }).appendTo("#TagList");
            })
        }
    })
}

    function queryCount() {
    $.ajax({
        url:"/BlogsCount",
        type:"GET",
        success:function (data){
            $("#blogCount").text(data.BlogCount);
            $("#commentCount").text(data.CommentCount);
            $("#TagCount").text(data.TagCount);
            $("#ViewCount").text(data.ViewCount);
        }
    })
}



    $(document).on("keyup","#Search_for_articles",function () {
    if(event.keyCode==13){
    window.location.href="/toblogSearch?title="+$.trim($("#Search_for_articles").val());
}
})

    $(document).on("click","#search_blog_btn",function () {
    window.location.href="/toblogSearch?title="+$.trim($("#Search_for_articles").val());
})

    function secondToDate(second) {
    if (!second) {
    return 0;
}
    var time = new Array(0, 0, 0, 0, 0);
    if (second >= 365 * 24 * 3600) {
    time[0] = parseInt(second / (365 * 24 * 3600));
    second %= 365 * 24 * 3600;
}
    if (second >= 24 * 3600) {
    time[1] = parseInt(second / (24 * 3600));
    second %= 24 * 3600;
}
    if (second >= 3600) {
    time[2] = parseInt(second / 3600);
    second %= 3600;
}
    if (second >= 60) {
    time[3] = parseInt(second / 60);
    second %= 60;
}
    if (second > 0) {
    time[4] = second;
}
    return time;
}
    function setTime() {
    /*此处为网站的创建时间*/
    var create_time = Math.round(new Date(Date.UTC(2022, 05, 22, 18, 01, 01)).getTime() / 1000);
    var timestamp = Math.round((new Date().getTime() + 8 * 60 * 60 * 1000) / 1000);
    currentTime = secondToDate((timestamp - create_time));
    currentTimeHtml = '本站已运行：' + currentTime[0] + '年' + currentTime[1] + '天'
    + currentTime[2] + '时' + currentTime[3] + '分' + currentTime[4]
    + '秒';
    document.getElementById("htmer_time").innerHTML = currentTimeHtml;
}
    setInterval(setTime, 1000);
