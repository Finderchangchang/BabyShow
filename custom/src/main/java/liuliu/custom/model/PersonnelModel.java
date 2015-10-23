package liuliu.custom.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/9/30.
 */
public class PersonnelModel implements Serializable {
    public int id;
    public String PersonnelId;//人员编码
    public String PersonnelStateName;//人员状态
    public String OrganizationId;//单位编码
    public String PersonnelName;//姓名
    public String PersonnelIdentityNumber;//证件号码
    public String PersonnelSex;//性别
    public String PersonnelNationName;//民族
    public String PersonnelPost;//职务
    public String PersonnelPhoneNumber;//手机号码
    public String PersonnelEmail;//邮箱
    public String PersonnelArrivalTypeName;//交通方式
    public String PersonnelSerialNumber;//顺序号
    public String HotelName;//酒店编码
    public String RoomNumber;//入住房间编码
    public String PersonnelArrivalTime;//到达时间
    public String CompanyName;
    public String ChangeTime;
    public String MeetingSign;//是否参加会议签到
    public String LunchSign;//是否参加午饭签到
    public String DinnerSign;//是否参加晚饭签到
    public String TouristSign;//是否参加旅游签到
    public String GiftSign;//是否参加旅游签到

    public String getChangeTime() {
        return ChangeTime;
    }

    public void setChangeTime(String changeTime) {
        ChangeTime = changeTime;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonnelId() {
        return PersonnelId;
    }

    public void setPersonnelId(String personnelId) {
        PersonnelId = personnelId;
    }

    public String getPersonnelStateName() {
        return PersonnelStateName;
    }

    public void setPersonnelStateName(String personnelStateName) {
        PersonnelStateName = personnelStateName;
    }

    public String getOrganizationId() {
        return OrganizationId;
    }

    public void setOrganizationId(String organizationId) {
        OrganizationId = organizationId;
    }

    public String getPersonnelName() {
        return PersonnelName;
    }

    public void setPersonnelName(String personnelName) {
        PersonnelName = personnelName;
    }

    public String getPersonnelIdentityNumber() {
        return PersonnelIdentityNumber;
    }

    public void setPersonnelIdentityNumber(String personnelIdentityNumber) {
        PersonnelIdentityNumber = personnelIdentityNumber;
    }

    public String getPersonnelSex() {
        return PersonnelSex;
    }

    public void setPersonnelSex(String personnelSex) {
        PersonnelSex = personnelSex;
    }

    public String getPersonnelNationName() {
        return PersonnelNationName;
    }

    public void setPersonnelNationName(String personnelNationName) {
        PersonnelNationName = personnelNationName;
    }

    public String getPersonnelPost() {
        return PersonnelPost;
    }

    public void setPersonnelPost(String personnelPost) {
        PersonnelPost = personnelPost;
    }

    public String getPersonnelPhoneNumber() {
        return PersonnelPhoneNumber;
    }

    public void setPersonnelPhoneNumber(String personnelPhoneNumber) {
        PersonnelPhoneNumber = personnelPhoneNumber;
    }

    public String getPersonnelEmail() {
        return PersonnelEmail;
    }

    public void setPersonnelEmail(String personnelEmail) {
        PersonnelEmail = personnelEmail;
    }

    public String getPersonnelArrivalTypeName() {
        return PersonnelArrivalTypeName;
    }

    public void setPersonnelArrivalTypeName(String personnelArrivalTypeName) {
        PersonnelArrivalTypeName = personnelArrivalTypeName;
    }

    public String getPersonnelSerialNumber() {
        return PersonnelSerialNumber;
    }

    public void setPersonnelSerialNumber(String personnelSerialNumber) {
        PersonnelSerialNumber = personnelSerialNumber;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public String getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        RoomNumber = roomNumber;
    }

    public String getPersonnelArrivalTime() {
        return PersonnelArrivalTime;
    }

    public void setPersonnelArrivalTime(String personnelArrivalTime) {
        PersonnelArrivalTime = personnelArrivalTime;
    }

    public String getMeetingSign() {
        return MeetingSign;
    }

    public void setMeetingSign(String meetingSign) {
        MeetingSign = meetingSign;
    }

    public String getLunchSign() {
        return LunchSign;
    }

    public void setLunchSign(String lunchSign) {
        LunchSign = lunchSign;
    }

    public String getDinnerSign() {
        return DinnerSign;
    }

    public void setDinnerSign(String dinnerSign) {
        DinnerSign = dinnerSign;
    }

    public String getTouristSign() {
        return TouristSign;
    }

    public void setTouristSign(String touristSign) {
        TouristSign = touristSign;
    }

    public String getGiftSign() {
        return GiftSign;
    }

    public void setGiftSign(String giftSign) {
        GiftSign = giftSign;
    }
}
