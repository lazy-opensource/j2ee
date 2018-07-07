
<script>
    var contextPath = '${contextPath}';
</script>
<script type="text/javascript"  src="${contextPath}/static/js/demo/demo.js"></script>
<div id="searchTab" style="padding:3px">
    <span>姓名:</span>
    <input id="c_name" style="border-radius:10px;cursor:pointer;text-indent:12px;width='100'">
    <span>年龄:</span>
    <input id="c_age" style="border-radius:10px;cursor:pointer;text-indent:12px;width='30'">
    <span>-</span>
    <input id="c_age2" style="border-radius:10px;cursor:pointer;text-indent:12px;width='30'">
    <span>地址:</span>
    <input id="c_address" style="border-radius:10px;cursor:pointer;text-indent:12px;width='100'"><br/>
    <span>编号:</span>
    <input id="c_no" style="border-radius:10px;cursor:pointer;text-indent:12px;width='80'">
    <span>性别:</span>
    <select id="c_gender">
        <option name="c_gender" value="">--请选择--</option>
        <option name="c_gender" value="1">男</option>
        <option name="c_gender" value="2">女</option>
    </select>
    <span>金额:</span>
    <input id="c_money" style="border-radius:10px;cursor:pointer;text-indent:12px;width='100'"><br/>
    <span>创建时间:</span>
    <input id="c_createTimeS" class="easyui-datetimebox" data-options="editable:false">
    <span>-</span>
    <input id="c_createTimeE" class="easyui-datetimebox" data-options="editable:false">
    <span>修改时间:</span>
    <input id="c_updateTimeS" class="easyui-datetimebox" data-options="editable:false">
    <span>-</span>
    <input id="c_updateTimeE" class="easyui-datetimebox" data-options="editable:false">
    <a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">查询</a>
    <a href="#" class="easyui-linkbutton" plain="true" onclick="clearSearch()">清空</a>
</div>
<hr>
<#--主数据表-->
<table id="demo_list"></table>

<#include "./add.ftl">
<#include "./edit.ftl">