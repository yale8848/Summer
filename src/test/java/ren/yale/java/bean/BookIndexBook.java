package ren.yale.java.bean;

/**
 * Created by yale on 2017/7/14.
 */

public class BookIndexBook {


    /**
     * edition : 0
     * status : 1
     * editionName :
     * subject : 2
     * noEditionName : 中考新思路
     * examQuestionNum : 283
     * vipFree : 1
     * image : https://lftresource.oss-cn-qingdao.aliyuncs.com/LFT-GuidanceLearn/cover/bookCover_8120d0ce053edd4d5c26341cc39c6e7b.png
     * type : 14
     * section : 2
     * freePageNum : 5
     * id : 68257
     * gradeName : 九年级下册
     * questionNum : 981
     * subjectName : 化学
     * price : 10.0
     * name : 中考新思路
     * volume : 2
     * tagOne : 重点题占比28.85%
     * grade : 9
     * examRate : 28.85
     * permissions : 1
     * buyStatus : 0
     */

    private BookBean book;
    /**
     * book : {"edition":"0","status":"1","editionName":"","subject":"2","noEditionName":"中考新思路","examQuestionNum":"283","vipFree":"1","image":"https://lftresource.oss-cn-qingdao.aliyuncs.com/LFT-GuidanceLearn/cover/bookCover_8120d0ce053edd4d5c26341cc39c6e7b.png","type":"14","section":"2","freePageNum":"5","id":"68257","gradeName":"九年级下册","questionNum":"981","subjectName":"化学","price":"10.0","name":"中考新思路","volume":"2","tagOne":"重点题占比28.85%","grade":"9","examRate":"28.85","permissions":1,"buyStatus":0}
     * dxhMode : 2
     * errorcode : 0
     * message :
     * success : true
     */

    private int dxhMode;
    private int errorcode;
    private String message;
    private boolean success;

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public int getDxhMode() {
        return dxhMode;
    }

    public void setDxhMode(int dxhMode) {
        this.dxhMode = dxhMode;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class BookBean {
        private String edition;
        private String status;
        private String editionName;
        private String subject;
        private String noEditionName;
        private String examQuestionNum;
        private String vipFree;
        private String image;
        private String type;
        private String section;
        private String freePageNum;
        private String id;
        private String gradeName;
        private String questionNum;
        private String subjectName;
        private String price;
        private String name;
        private String volume;
        private String tagOne;
        private String grade;
        private String examRate;
        private int permissions;
        private int buyStatus;

        public String getEdition() {
            return edition;
        }

        public void setEdition(String edition) {
            this.edition = edition;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getEditionName() {
            return editionName;
        }

        public void setEditionName(String editionName) {
            this.editionName = editionName;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getNoEditionName() {
            return noEditionName;
        }

        public void setNoEditionName(String noEditionName) {
            this.noEditionName = noEditionName;
        }

        public String getExamQuestionNum() {
            return examQuestionNum;
        }

        public void setExamQuestionNum(String examQuestionNum) {
            this.examQuestionNum = examQuestionNum;
        }

        public String getVipFree() {
            return vipFree;
        }

        public void setVipFree(String vipFree) {
            this.vipFree = vipFree;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getFreePageNum() {
            return freePageNum;
        }

        public void setFreePageNum(String freePageNum) {
            this.freePageNum = freePageNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

        public String getQuestionNum() {
            return questionNum;
        }

        public void setQuestionNum(String questionNum) {
            this.questionNum = questionNum;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getTagOne() {
            return tagOne;
        }

        public void setTagOne(String tagOne) {
            this.tagOne = tagOne;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getExamRate() {
            return examRate;
        }

        public void setExamRate(String examRate) {
            this.examRate = examRate;
        }

        public int getPermissions() {
            return permissions;
        }

        public void setPermissions(int permissions) {
            this.permissions = permissions;
        }

        public int getBuyStatus() {
            return buyStatus;
        }

        public void setBuyStatus(int buyStatus) {
            this.buyStatus = buyStatus;
        }
    }
}
