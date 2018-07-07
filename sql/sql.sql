--第一步：创建表
-- drop table t_demo; --如果表已经存在可以执行drop把它删除
create table t_demo(
   id number, constraint t_demo_pk primary key(id), --唯一标识
   demo_name varchar2(32) not null, --名称
   demo_money number(9,2) default 1000.00 not null, --金额
   demo_no varchar2(32) not null, --编号
   demo_address varchar2(50), --地址
   demo_age number(3), constraint t_demo_age_ck check(demo_age >= 16 or demo_age <= 65), --年龄
   demo_gender number(1) constraint t_demo_gender_ck check(demo_gender = 1 or demo_gender = 2), --性别
   create_time date default sysdate not null, --创建时间
   update_time date default sysdate not null, --修改时间
   remark varchar2(30) --备注
);

--第二步：创建自增序列
--drop sequence t_demo_seq; --如果序列已经存在可以执行drop把它删除
create sequence t_demo_seq
maxvalue 999999
minvalue 1
increment by 1
nocache
nocycle
start with 1;

--创建id为null时自增触发器
create or replace trigger t_demo_insert
before insert
on t_demo for each row
when (new.id is null)
begin
  select t_demo_seq.nextval into :new.id from dual;
end;

insert into t_demo(
  demo_name,
  demo_money,
  demo_no,
  demo_age,
  demo_gender,
  demo_address,
  create_time
)values(
  '刘备',
  15000.66,
  'LIUBEI',
  30,
  1,
  '三国',
  to_date('1917-08-09 11:12:12', 'yyyy-MM-dd HH24:MI:SS')
);
commit;

select * from t_demo;
commit;