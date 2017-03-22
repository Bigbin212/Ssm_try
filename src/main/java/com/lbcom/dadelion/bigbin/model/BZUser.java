package com.lbcom.dadelion.bigbin.model;

import java.util.Date;

public class BZUser {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_z_user.xlh
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    private String xlh;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_z_user.username
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_z_user.password
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_z_user.ip
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_z_user.email
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column b_z_user.phone
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    private String phone;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_z_user.xlh
     *
     * @return the value of b_z_user.xlh
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    public String getXlh() {
        return xlh;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_z_user.xlh
     *
     * @param xlh the value for b_z_user.xlh
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_z_user.username
     *
     * @return the value of b_z_user.username
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_z_user.username
     *
     * @param username the value for b_z_user.username
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_z_user.password
     *
     * @return the value of b_z_user.password
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_z_user.password
     *
     * @param password the value for b_z_user.password
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_z_user.ip
     *
     * @return the value of b_z_user.ip
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_z_user.ip
     *
     * @param ip the value for b_z_user.ip
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_z_user.email
     *
     * @return the value of b_z_user.email
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_z_user.email
     *
     * @param email the value for b_z_user.email
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column b_z_user.phone
     *
     * @return the value of b_z_user.phone
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column b_z_user.phone
     *
     * @param phone the value for b_z_user.phone
     *
     * @mbggenerated Fri Apr 08 12:21:30 CST 2016
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }	
    
    private Date zcsj;

	public Date getZcsj() {
		return zcsj;
	}

	public void setZcsj(Date zcsj) {
		this.zcsj = zcsj;
	}
    
	private String yhqx;

	public String getYhqx() {
		return yhqx;
	}

	public void setYhqx(String yhqx) {
		this.yhqx = yhqx;
	}
	private String photo;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}