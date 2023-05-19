package de.fhswf.genericapplication.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tests")
public class Test extends BaseEntity {
    private boolean boolValue;

    private Boolean nullableBoolValue;

    private byte byteValue;

    private Byte nullableByteValue;

    private short shortValue;

    private Short nullableShortValue;

    private int intValue;

    private Integer nullableIntValue;

    private long longValue;

    private Long nullableLongValue;

    private float floatValue;

    private Float nullableFloatValue;

    private double doubleValue;

    private Double nullableDoubleValue;

    private BigDecimal bigDecimalValue;

    private String stringValue;

    private char charValue;

    private Character nullableCharValue;

    private Date dateValue;

    /**
     * Default constructor
     */
    public Test() {
        super();
    }

    public Test(
            boolean boolValue, Boolean nullableBoolValue,
            byte byteValue,
            Byte nullableByteValue,
            short shortValue,
            Short nullableShortValue,
            int intValue,
            Integer nullableIntValue,
            long longValue,
            Long nullableLongValue,
            float floatValue,
            Float nullableFloatValue,
            double doubleValue,
            Double nullableDoubleValue,
            BigDecimal bigDecimalValue,
            String stringValue,
            char charValue,
            Character nullableCharValue,
            Date dateValue
    ) {
        this.boolValue = boolValue;
        this.nullableBoolValue = nullableBoolValue;
        this.byteValue = byteValue;
        this.nullableByteValue = nullableByteValue;
        this.shortValue = shortValue;
        this.nullableShortValue = nullableShortValue;
        this.intValue = intValue;
        this.nullableIntValue = nullableIntValue;
        this.longValue = longValue;
        this.nullableLongValue = nullableLongValue;
        this.floatValue = floatValue;
        this.nullableFloatValue = nullableFloatValue;
        this.doubleValue = doubleValue;
        this.nullableDoubleValue = nullableDoubleValue;
        this.bigDecimalValue = bigDecimalValue;
        this.stringValue = stringValue;
        this.charValue = charValue;
        this.nullableCharValue = nullableCharValue;
        this.dateValue = dateValue;
    }

    public boolean isBoolValue() {
        return boolValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public Boolean getNullableBoolValue() {
        return nullableBoolValue;
    }

    public void setNullableBoolValue(Boolean nullableBoolValue) {
        this.nullableBoolValue = nullableBoolValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }

    public Byte getNullableByteValue() {
        return nullableByteValue;
    }

    public void setNullableByteValue(Byte nullableByteValue) {
        this.nullableByteValue = nullableByteValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public void setShortValue(short shortValue) {
        this.shortValue = shortValue;
    }

    public Short getNullableShortValue() {
        return nullableShortValue;
    }

    public void setNullableShortValue(Short nullableShortValue) {
        this.nullableShortValue = nullableShortValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public Integer getNullableIntValue() {
        return nullableIntValue;
    }

    public void setNullableIntValue(Integer nullableIntValue) {
        this.nullableIntValue = nullableIntValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public Long getNullableLongValue() {
        return nullableLongValue;
    }

    public void setNullableLongValue(Long nullableLongValue) {
        this.nullableLongValue = nullableLongValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public Float getNullableFloatValue() {
        return nullableFloatValue;
    }

    public void setNullableFloatValue(Float nullableFloatValue) {
        this.nullableFloatValue = nullableFloatValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Double getNullableDoubleValue() {
        return nullableDoubleValue;
    }

    public void setNullableDoubleValue(Double nullableDoubleValue) {
        this.nullableDoubleValue = nullableDoubleValue;
    }

    public BigDecimal getBigDecimalValue() {
        return bigDecimalValue;
    }

    public void setBigDecimalValue(BigDecimal bigDecimalValue) {
        this.bigDecimalValue = bigDecimalValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public Character getNullableCharValue() {
        return nullableCharValue;
    }

    public void setNullableCharValue(Character nullableCharValue) {
        this.nullableCharValue = nullableCharValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }
}
