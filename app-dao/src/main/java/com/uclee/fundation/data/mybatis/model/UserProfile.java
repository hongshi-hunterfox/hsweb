package com.uclee.fundation.data.mybatis.model;

import java.util.Date;

public class UserProfile {
	
	private Integer profileId;

    private Integer userId;

    private String identityCard;

    private String image;

    private String name;
    
    private String nickName;

    private String gender;

    private String phone;

    private String email;

    private Short loginCount;

    private Date registTime;
    
    private String registTimeStr;

    private Boolean isActive;
    
    private String targetPhone;
    
    private String ipAddr;
    
    private String serialNum;
    
    private String vipImage;
    
    private String vipJbarcode;
    
    private Date birth;
    
    private String birthStr;
    
    private Boolean isLunar;
    
    private String lastBuyStr;
    
    private Date lastBuy;
    
    private String cardNum;
    
    private String birthday;
    
    private String age;

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getLastBuyStr() {
		return lastBuyStr;
	}

	public void setLastBuyStr(String lastBuyStr) {
		this.lastBuyStr = lastBuyStr;
	}

	public Date getLastBuy() {
		return lastBuy;
	}

	public void setLastBuy(Date lastBuy) {
		this.lastBuy = lastBuy;
	}

	public String getBirthStr() {
		return birthStr;
	}

	public void setBirthStr(String birthStr) {
		this.birthStr = birthStr;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Boolean getIsLunar() {
		return isLunar;
	}

	public void setIsLunar(Boolean isLunar) {
		this.isLunar = isLunar;
	}

	public String getVipJbarcode() {
		return vipJbarcode;
	}

	public void setVipJbarcode(String vipJbarcode) {
		this.vipJbarcode = vipJbarcode;
	}

	public String getRegistTimeStr() {
		return registTimeStr;
	}

	public void setRegistTimeStr(String registTimeStr) {
		this.registTimeStr = registTimeStr;
	}

	public String getVipImage() {
		return vipImage;
	}

	public void setVipImage(String vipImage) {
		this.vipImage = vipImage;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTargetPhone() {
		return targetPhone;
	}

	public void setTargetPhone(String targetPhone) {
		this.targetPhone = targetPhone;
	}

	public UserProfile(){
    	super();
    }
    
    public UserProfile(Integer userId) {
		super();
		this.userId = userId;
	}
    
    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard == null ? null : identityCard.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Short getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Short loginCount) {
        this.loginCount = loginCount;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}