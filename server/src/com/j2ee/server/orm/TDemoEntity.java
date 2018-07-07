package com.j2ee.server.orm;

import com.j2ee.server.annotation.IgnoreReflect;
import com.j2ee.server.annotation.RangeQuery;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by lzy on 2017/8/31.
 */
@Entity
@Table(name = "T_DEMO", schema = "SCOTT", catalog = "")
//必须指定allocationSize 属性，代表步进，否则会出现负数情况
@SequenceGenerator(sequenceName = "t_demo_seq", name = "demoId",allocationSize=1)
public class TDemoEntity implements Serializable {

    @IgnoreReflect
    static final long serialVersionUID = 95276666L;

    private long id;
    private String demoName;
    private BigDecimal demoMoney;
    private String demoNo;
    private String demoAddress;
    private Byte demoAge;
    @RangeQuery
    private Byte demoAge2;
    private Integer demoGender;
    private Timestamp createTime;
    @RangeQuery(suffix = "E")
    private Timestamp createTimeE;
    @RangeQuery(suffix = "End")
    private Timestamp updateTimeEnd;
    private Timestamp updateTime;
    private String remark;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "demoId")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DEMO_NAME", nullable = false, length = 32)
    public String getDemoName() {
        return demoName;
    }

    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }

    @Transient
    public Byte getDemoAge2() {
        return demoAge2;
    }

    public void setDemoAge2(Byte demoAge2) {
        this.demoAge2 = demoAge2;
    }

    @Transient
    public Timestamp getCreateTimeE() {
        return createTimeE;
    }

    public void setCreateTimeE(Timestamp createTimeE) {
        this.createTimeE = createTimeE;
    }

    @Transient
    public Timestamp getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(Timestamp updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

    @Basic
    @Column(name = "DEMO_MONEY", nullable = false, precision = 2)
    public BigDecimal getDemoMoney() {
        return demoMoney;
    }

    public void setDemoMoney(BigDecimal demoMoney) {
        this.demoMoney = demoMoney;
    }

    @Basic
    @Column(name = "DEMO_NO", nullable = false, length = 32)
    public String getDemoNo() {
        return demoNo;
    }

    public void setDemoNo(String demoNo) {
        this.demoNo = demoNo;
    }

    @Basic
    @Column(name = "DEMO_ADDRESS", nullable = true, length = 50)
    public String getDemoAddress() {
        return demoAddress;
    }

    public void setDemoAddress(String demoAddress) {
        this.demoAddress = demoAddress;
    }

    @Basic
    @Column(name = "DEMO_AGE", nullable = true, precision = 0)
    public Byte getDemoAge() {
        return demoAge;
    }

    public void setDemoAge(Byte demoAge) {
        this.demoAge = demoAge;
    }

    @Basic
    @Column(name = "DEMO_GENDER", nullable = true, precision = 0)
    public Integer getDemoGender() {
        return demoGender;
    }

    public void setDemoGender(Integer demoGender) {
        this.demoGender = demoGender;
    }

    @Basic
    @Column(name = "CREATE_TIME", nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "UPDATE_TIME", nullable = false)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "REMARK", nullable = true, length = 30)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TDemoEntity that = (TDemoEntity) o;

        if (id != that.id) return false;
        if (demoMoney != that.demoMoney) return false;
        if (demoName != null ? !demoName.equals(that.demoName) : that.demoName != null) return false;
        if (demoNo != null ? !demoNo.equals(that.demoNo) : that.demoNo != null) return false;
        if (demoAddress != null ? !demoAddress.equals(that.demoAddress) : that.demoAddress != null) return false;
        if (demoAge != null ? !demoAge.equals(that.demoAge) : that.demoAge != null) return false;
        if (demoGender != null ? !demoGender.equals(that.demoGender) : that.demoGender != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (demoName != null ? demoName.hashCode() : 0);
        result = 31 * result + (demoMoney != null ? (demoMoney.hashCode()) : 0);
        result = 31 * result + (demoNo != null ? demoNo.hashCode() : 0);
        result = 31 * result + (demoAddress != null ? demoAddress.hashCode() : 0);
        result = 31 * result + (demoAge != null ? demoAge.hashCode() : 0);
        result = 31 * result + (demoGender != null ? demoGender.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
