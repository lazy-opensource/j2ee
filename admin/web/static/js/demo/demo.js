/**
 * Created by lzy on 2017/8/29.
 */
var demo_datagrid;
$(function(){
    /**
     * list
     */
    demo_datagrid = $('#demo_list').datagrid({
        url:  contextPath + '/sysback/demo/query' ,//请求后台加载数据
        //默认是POST请求方式
        method: 'POST',
        idField: 'id', //指示哪个字段是一个标识字段
        fitColumns:true, //自动展开/收缩列的大小以适应网格宽度并防止水平滚动
        striped: true, //True对条进行条带化
        pageSize:10,
        pageList: [10, 20, 30, 40, 50],
        pagination: true,
        sortName: 'updateTime', //定义哪个列排序
        sortOrder: 'desc', //定义列排序顺序，只能是'asc'或'desc'。
        checkOnSelect: true, //如果为true，当用户点击一行时，该复选框被选中/取消选中
        selectOnCheck: true, //如果设置为true，则单击复选框将始终选择该行。如果为false，则选择一行不会选中该复选框
        singleSelect:false, //允许只选择一行
        rownumbers:true, //显示行号列
        queryParams:{}, //请求远程数据时，发送附加参数
        loadFilter:function(data){ //返回过滤后的数据显示。该函数采用一个指示原始数据的参数“data”。
            // 您可以将原始源数据更改为标准数据格式。此函数必须返回包含“total”和“rows”属性的标准数据对象。
            if(data && data.rows <= 0){
                $.messager.show({
                    title:'系统提示!',
                    msg: '查无数据!',
                    timeout:3000,
                    showType:'slide'
                });
            }
            return data;
        },
        columns: [[
            //width: 列的宽度。如果未定义，宽度将自动展开以适应其内容。没有宽度定义会降低性能
            //field: 列字段名称。
            //title: 列标题文本。
            {field:'id',title:'No',width:30,checkbox:true},
            {field:'demoName',title:'姓名',width:30,sortable:false,resizable:true,
                // 单元格格式化函数，取三个参数：
                // value：字段值。
                // rowData：行记录数据。
                // rowIndex：行索引。
                formatter:function(value,row,index){
                            if(row.demoName){
                                return row.demoName;
                            } else {
                                return "该字段没有数据";
                            }
            }},
            {field:'demoAddress',title:'地址',width:60,resizable:true,sortable:false},
            {field:'demoAge',title:'年龄',width:20,resizable:true,sortable:true},
            {field:'demoGender',title:'性别',width:20,resizable:true,sortable:true,
                 formatter: function (value, row, index) {
                     if (row.demoGender == 1){
                         return '男';
                     }else if (row.demoGender ==2){
                         return '女';
                     }
                 }
            },
            {field:'demoMoney',title:'金额',resizable:true,width:40,sortable:true},
            {field:'demoNo',title:'编号',resizable:true,width:40,sortable:false},
            {field:'createTime',title:'创建时间',resizable:true,width:70,sortable:true},
            {field:'updateTime',title:'修改时间',resizable:true,width:70,sortable:true},
            {field:'remark',title:'备注',width:60,resizable:true,sortable:false}
        ]],
        onBeforeRender:function(target, rowIndex){ //在视图呈现之前触发

        },
        onAfterRender: function(target, rows){ //渲染视图后触发

        },
        onLoadSuccess: function(data){ //成功加载数据时触发。

        },
        onLoadError: function(){ //在加载远程数据时发生一些错误时触发

        },
        loading: true,
        toolbar: [{
            iconCls: 'icon-edit',
            handler: function(){
                toEdit();
            }
        },'-',{
            iconCls: 'icon-add',
            handler: function(){
                $('#demo_add').dialog('open');
            }
        },'-',{
            iconCls: 'icon-no',
            handler: function(){
                del();
            }
        },'_',{
            iconCls: 'icon-reload',
            handler: function () {
                $('#demo_list').datagrid('clearChecked');
                $('#demo_list').datagrid('clearSelections');
            }
        }]
    });

    //定义验证规则
    $.extend($.fn.validatebox.defaults.rules, {
        age:{
            validator:function(value, param){
                if (value < 16 || value > 65){
                    return false;
                }
                return true;
            },
            message:'年龄只能在16-65范围之间'
        },
        maxlength:{
            validator: function (value, param) {
                return value.length <= param[0];
            },
            message: '长度不能大于{0}'
        },
        money:{
            validator: function (value, param) {
                var str = value.split('.');
                if (str[0].length > 7){
                    return false;
                }
                if (str && str.length == 2){
                    if (str[1].length > 2){
                        return false;
                    }
                }
                return true;
            },
            message: '金额数目只能输入7位整数和两位小数点'
        }
    })

    /*定义分页器*/
    // var pagination = demo_datagrid.datagrid("getPager");
    // if (pagination){
    //     pagination.pagination({
    //         pageList: [5,10,20,50,100],
    //         layout:['first','links','last','sep','prev','next','manual'],
    //         showRefresh:true,
    //         beforePageText:'页面',
    //         afterPageText:'of {pages}',
    //         displayMsg:'显示 {from} to {to} of {total} 页面信息'
    //     })
    // }
})

function confirmEdit(){
    $('#edit_form').form('submit', {
        url:  contextPath + '/sysback/demo/editDemo',
        onSubmit: function(){
            var isValid = $(this).form('validate');
            if (!isValid){
                $.messager.show({
                    title:'系统提示!',
                    msg: '表单字段验证不通过',
                    timeout:3000,
                    showType:'slide'
                });
            }
            return isValid;
        },
        success:function (data) {
            data = eval('(' + data + ')');
            if (data.success){
                $.messager.show({
                    title:'系统提示!',
                    msg: data.msg,
                    timeout:3000,
                    showType:'slide'
                });
                demo_datagrid.datagrid('load');
            }else {
                $.messager.show({
                    title:'系统提示!',
                    msg: '服务器异常',
                    timeout:3000,
                    showType:'slide'
                });
            }
            clearDataGrid();
            $('#demo_edit').dialog('close');
        }
    });
}

function cancleEdit(){
    clearDataGrid();
    $('#edit_form').form('clear');
    $('#demo_edit').dialog('close');
}

function toEdit(){
    var selectList = demo_datagrid.datagrid('getSelections');
    if (selectList && selectList.length == 1){
        var id = selectList[0].id;
        $.get(
            contextPath + '/sysback/demo/toEdit',
            {ids: id},
            function (data) {
                $('#edit_demo_address').val(data.demoAddress);
                $('#edit_demo_age').val(data.demoAge);
                $('#edit_demo_money').val(data.demoMoney);
                $('#edit_demo_id').val(data.id);
                $('#edit_demo_name').val(data.demoName);
                $('#edit_demo_no').val(data.demoNo);
                $('#edit_demo_remark').val(data.remark);
                $('input[name="demoGender"]:radio').each(function(){
                   if (this.value == data.demoGender){
                       this.checked = true;
                   }
                });
                $('#demo_edit').dialog('open');
            },'json'
        )
    }else {
        $.messager.alert('消息','请选择一项进行操作','info');
    }
}

function del(){
    var selectList = demo_datagrid.datagrid('getSelections');
    if (selectList && selectList.length > 0){
        $.messager.confirm('确认', '确认删除' + selectList.length + '条数据吗?', function(r){
            if (r){
                var ids = [];
                for (var i = 0; i < selectList.length; i++){
                    ids.push(selectList[i].id);
                }
                $.post(
                    contextPath + '/sysback/demo/del',
                    {ids: ids.join(',')},
                    function (data) {
                        if (data.success) {
                            $.messager.show({
                                title: '系统消息',
                                msg: data.msg,
                                timeout: 3000,
                                showType: 'slide'
                            });
                            demo_datagrid.datagrid('load');
                        } else {
                            $.messager.show({
                                title: '系统消息',
                                msg: '服务器错误',
                                timeout: 3000,
                                showType: 'slide'
                            })
                        }
                        clearDataGrid();
                        $('#demo_list').datagrid('clearChecked');
                        $('#demo_list').datagrid('clearSelections');
                    },'json'
                )
            }
        });
    }else {
        $.messager.alert('消息','请至少选择一项进行操作','info');
    }
}

/**
 * add
 */
function confirmAdd(){
    $('#add_form').form('submit', {
        url:  contextPath + '/sysback/demo/addDemo',
        onSubmit: function(){
            var isValid = $(this).form('validate');
            if (!isValid){
                $.messager.show({
                    title:'系统提示!',
                    msg: '表单字段验证不通过',
                    timeout:3000,
                    showType:'slide'
                });
            }
            return isValid;
        },
        success:function (data) {
            data = eval('(' + data + ')');
            if (data.success){
                $.messager.show({
                    title:'系统提示!',
                    msg: data.msg,
                    timeout:3000,
                    showType:'slide'
                });
                demo_datagrid.datagrid('load');
            }else {
                $.messager.show({
                    title:'系统提示!',
                    msg: '服务器异常',
                    timeout:3000,
                    showType:'slide'
                });
            }
            $('#add_form').form('clear');
            $('#demo_add').dialog('close');
            clearDataGrid();
        }
    });
}

/**
 * 取消添加
 */
function cancleAdd(){
    clearDataGrid();
    $('#add_form').form('clear');
    $('#demo_add').dialog('close');
}

function clearDataGrid(){
    $('#demo_list').datagrid('clearChecked');
    $('#demo_list').datagrid('clearSelections');
}

/**
 * 条件查找
 */
function doSearch(){
    var c_createTimeS = $('#c_createTimeS').val();
    var c_createTimeE = $('#c_createTimeE').val();
    var c_updateTimeS = $('#c_updateTimeS').val();
    var c_updateTimeE = $('#c_updateTimeE').val();
    var c_name = $('#c_name').val();
    var c_age = $('#c_age').val();
    var c_age2 = $('#c_age2').val();
    var c_address = $('#c_address').val();
    var c_money = $('#c_money').val();
    var c_no = $('#c_no').val();
    var c_gender = $('#c_gender').val();
    var conditionParams = [];
    conditionParams.push({updateTime: c_updateTimeS, rule: '>='});
    conditionParams.push({updateTimeEnd: c_updateTimeE, rule: '<='});
    conditionParams.push({createTimeE: c_createTimeE, rule: '<='});
    conditionParams.push({createTime: c_createTimeS, rule: '>='});
    conditionParams.push({demoGender: c_gender, rule: '='});
    conditionParams.push({demoNo: c_no, rule: 'like'});
    conditionParams.push({demoMoney: c_money, rule: '<='});
    conditionParams.push({demoAddress: c_address, rule: 'like'});
    conditionParams.push({demoAge2: c_age2, rule: '<='});
    conditionParams.push({demoAge: c_age, rule: '>='});
    conditionParams.push({demoName: c_name, rule: 'like'});

    $('#demo_list').datagrid('load',{
        conditionParams: JSON.stringify(conditionParams)
    });
}

function clearSearch(){
    $('#c_createTimeS').combo('setText','');
    $('#c_createTimeE').combo('setText','');
    $('#c_updateTimeS').combo('setText','');
    $('#c_updateTimeE').combo('setText','');
    $('#c_createTimeS').val('');
    $('#c_createTimeE').val('');
    $('#c_updateTimeS').val('');
    $('#c_updateTimeE').val('');
    $('#c_name').val('');
    $('#c_age').val('');
    $('#c_age2').val('');
    $('#c_address').val('');
    $('#c_money').val('');
     $('#c_no').val('');
    $('#c_gender').val('');
}