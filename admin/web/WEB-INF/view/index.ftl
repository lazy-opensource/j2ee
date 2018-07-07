<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>J2EE Project Combat</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/easyui/themes/default/easyui.css" >
    <script type="text/javascript" src="${contextPath}/static/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${contextPath}/static/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${contextPath}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>

<!--easyUI主体-->
<div id="main_layout" class="easyui-layout" style="width:100%; height:680px;"">
    <!-- 上 -->
    <div data-options="region:'north'" style="height: 70px;background-color:#95B8E7;">
        <span style="margin-left:0.5%;margin-top: 30px;">
            <font size="3" style="line-height: 5.0em;" color="#000000" >
                <b>欢迎登录J2EE项目实战后台管理</b>
            </font>
        </span>
        <span style="float:right;margin-top: 30px;margin-right:20px;">
            <font size="3" color="#000000">欢迎 : laizhiyuan</font>&nbsp;
            <a href="#" > <font size="3" color="red">退出</font> </a>
        </span>
    </div>

    <!-- 左边 -->
    <div data-options="region:'west',split:true" title="菜单列表" style="width: 200px;">
        <div class="easyui-accordion"  data-options="fit:true,border:false">
            <div title="Demo" data-options="iconCls:'icon-search'" style="padding:10px;">
                <ul id="demo_tree" class="easyui-tree">
                    <#--demo tree-->
                </ul>
            </div>
            <div title="系统权限" data-options="iconCls:'icon-search'" style="padding:10px;">
                <ul id="main_tree" class="easyui-tree">
                    <#--main tree-->
                </ul>
            </div>
            <div title="订单管理" data-options="iconCls:'icon-search'" style="padding:10px;">
                <ul id="main_tree" class="easyui-tree">
                <#--main tree-->
                </ul>
            </div>
            <div title="财务中心" data-options="iconCls:'icon-search'" style="padding:10px;">
                <ul id="main_tree" class="easyui-tree">
                <#--main tree-->
                </ul>
            </div>
        </div>
    </div>

    <!-- 中间 -->
    <div data-options="region:'center',title:'内容',iconCls:'icon-ok'">
        <div id="tabs" class="easyui-tabs" style="width:100%px;height:100%px;">

        </div>
    </div>

    <!-- 下 -->
    <div data-options="region:'south',split:true" style="height:40px;background-color:#95B8E7;">
        <div style="" align="center" style="margin-top: 17px;">
            <font size="" color="#000000">J2EE企业级开发实战  @版权所有：laizhiyuan</font>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        var height1 = $(window).height()-20;
        $("#main_layout").attr("style","width:100%;height:"+height1+"px");
        $("#main_layout").layout("resize",{
            width:"100%",
            height:height1+"px"
        });
    });

    $(window).resize(function(){
        var height1 = $(window).height()-30;
        $("#main_layout").attr("style","width:100%;height:"+height1+"px");
        $("#main_layout").layout("resize",{
            width:"100%",
            height:height1+"px"
        });
    });

    /*Tree 对象数组*/
    var demoMenuTreeJsonData = [{
        "id": "-1",
        "text":"Demo",
        "state":"open",
        "children":[{
            "id": "-2",
            "text":"列表",
            "checked":false,
            "attributes":{
                "url":"${contextPath}/sysback/demo/list"
            }
        }]
    }];

    $('#demo_tree').tree({
        data: demoMenuTreeJsonData,
        onClick: function(node){
            addToTabs({
                title: node.text,
                href: node.attributes.url,
                closable:true
            });
        }
    });

    function addToTabs(opts){
        var main_tabs = $('#tabs');
        if(main_tabs.tabs('exists', opts.title)){
            main_tabs.tabs('select', opts.title);
        }else{
            main_tabs.tabs('add', opts);
        }
    }

</script>
</body>
</html>