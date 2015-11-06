/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodelspike.concrete;

import java.sql.Date;

/**
 *
 * @author boydj
 */
public class TableObject {
    
    private final Long longVal;
    private final String stringVal;
    private final Date dateVal;
    
    public TableObject(Long longVal, String stringVal, Date dateVal) {
        this.longVal = longVal;
        this.stringVal = stringVal;
        this.dateVal = dateVal;
    }
    
    public Long getLongVal() {
        return longVal;
    }
    
    public String getStringVal() {
        return stringVal;
    }
    
    public Date getDateVal() {
        return dateVal;
    }
    
}
