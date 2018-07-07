<div id="demo_edit" class="easyui-dialog" title="Edit Demo" style="width:360px;height:400px;padding:40px 50px"
     data-options="iconCls:'icon-add',closable:false,resizable:true,modal:true,closed:true,buttons:'#edit_buttons'">
    <form id="edit_form" method="post">
        <input type="hidden" name="id" id="edit_demo_id" >
        <div>
            <label for="demoName">姓名:</label>
            <input class="easyui-validatebox" id="edit_demo_name"  type="text" name="demoName" data-options="required:true, validType:'maxlength[32]'" />
        </div><br/>
        <div>
            <label for="demoAge">年龄:</label>
            <input class="easyui-validatebox" id="edit_demo_age"  type="text" name="demoAge" data-options="required:true, validType:'age'" />
        </div><br/>
        <div>
            <label for="demoAddress">地址:</label>
            <input class="easyui-validatebox" id="edit_demo_address" type="text" name="demoAddress" data-options="required:true,validType:'maxlength[50]'" />
        </div><br/>
        <div>
            <label for="demoGender">性别:</label>
            <input class="easyui-validatebox"  type="radio" value="1" name="demoGender" data-options="required:true">男
            <input class="easyui-validatebox"  type="radio" value="2" name="demoGender" data-options="required:true">女
        </div><br/>
        <div>
            <label for="demoNo">编号:</label>
            <input class="easyui-validatebox" id="edit_demo_no" type="text" name="demoNo" data-options="required:true" />
        </div><br/>
        <div>
            <label for="demoMoney">金额:</label>
            <input class="easyui-validatebox" id="edit_demo_money"  type="text" name="demoMoney" data-options="required:true,validType:'money'" />
        </div><br/>
        <div>
            <label for="remark">备注:</label>
            <input class="easyui-validatebox" id="edit_demo_remark"  data-options="validType:'maxlength[30]'" type="text" name="remark" />
        </div>
    </form>
</div>

<div id="edit_buttons">
    <a href="javascript:confirmEdit();" class="easyui-linkbutton">确认</a>
    <a href="javascript:cancleEdit();" class="easyui-linkbutton">取消</a>
</div>