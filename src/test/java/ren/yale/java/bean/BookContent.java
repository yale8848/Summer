package ren.yale.java.bean;

public class BookContent {
	private Integer id;
	private String grade;
	private String subject;
	private String dxh;
	private String pageNum;
	private String questionNum;
	private String sid;
	private Integer pid;
	private String title;
	private String image;
	private Integer order;
	private Integer bookId;
	private String ids;
	private String ocrResult;
	private String bookName;
	private Integer favoritsCount;
	private Integer commentCount;
	private Integer praiseCount;
	private Integer sign;
	private String press;
	private String section;
	/*** 导学号类型 0：普通题目  1：影音题目   2：试卷目录*/
	private Integer type;
	private Integer isshow_jhpassword;
	
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	 
	public String getDxh() {
		return dxh;
	}
	public void setDxh(String dxh) {
		this.dxh = dxh;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(String questionNum) {
		this.questionNum = questionNum;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getOcrResult() {
		return ocrResult;
	}
	public void setOcrResult(String ocrResult) {
		this.ocrResult = ocrResult;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public Integer getFavoritsCount() {
		return favoritsCount;
	}
	public void setFavoritsCount(Integer favoritsCount) {
		this.favoritsCount = favoritsCount;
	}
	public Integer getSign() {
		return sign;
	}
	public void setSign(Integer sign) {
		this.sign = sign;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public Integer getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public Integer getIsshow_jhpassword() {
		return isshow_jhpassword;
	}
	public void setIsshow_jhpassword(Integer isshow_jhpassword) {
		this.isshow_jhpassword = isshow_jhpassword;
	}

}
