package org.platform.wwwroot.entity;

import org.framework.entity.Root;
import org.framework.utils.TimeUtils;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.Lob;
import org.zyweistartframework.persistence.annotation.ManyToOne;
import org.zyweistartframework.persistence.annotation.Table;
import org.zyweistartframework.persistence.annotation.Temporal;
/**
 * 资讯
 * @author Start
 */
@Entity("information")
@Table("WWW_INFORMATION")
public class Information extends Root {

	private static final long serialVersionUID = 1L;
	
	public Information(){}
	/**
	 * 资讯标题
	 */
	@Column(length=150,nullable=false)
	private String title;
	/**
	 * 关键字
	 */
	@Column(length=50)
	private String keyword;
	/**
	 * 作者
	 */
	@Column(length=50)
	private String author;
	/**
	 * 来源
	 */
	@Column(length=50)
	private String source;
	/**
	 * 来源时间
	 */
	@Temporal
	private String sourceTime;
	/**
	 * 主体内容
	 */
	@Lob
	private String content;
	/**
	 * 主体内容的简要描述
	 */
	@Column(length=200)
	private String description;
	/**
	 * 点击率
	 */
	@Column(nullable=false)
	private Integer hits=0;
	/**
	 * 访问级别(true:外网可以访问,false:只允许内网访问)
	 */
	@Column(nullable=false)
	private Boolean publicity=true;
	/**
	 * 首页显示
	 */
	@Column(nullable=false)
	private Boolean home=true;
	/**
	 * 资讯置顶
	 */
	@Column(nullable=false)
	private Boolean top=false;
	/**
	 * 资讯推荐
	 */
	@Column(nullable=false)
	private Boolean recommend=false;
	/**
	 * 是否发布
	 */
	@Column(nullable=false)
	private Boolean publish=false;
	/**
	 * 发布时间
	 */
	@Temporal
	private String publishTime;
	/**
	 * 开始发布时间
	 */
	@Temporal(nullable=false)
	private String startPublishTime=TimeUtils.getSysdateTimeStart();
	/**
	 * 结束发布时间
	 */
	@Temporal(nullable=false)
	private String endPublishTime="2099-12-31 23:59:59";
	/**
	 * 缩略图路径
	 * 路径:缩略图路径/日期(yyyyMMdd)/32位MD5随机文件名称.扩展名
	 */
	@Column(length=50)
	private String thumbnail;
	/**
	 * 静态页面路径
	 * 路径:静态页面路径/日期(yyyyMMdd)/32位MD5随机文件名称.扩展名
	 */
	@Column(length=50)
	private String page;
	/**
	 * 静态页面模板
	 */
	@ManyToOne
	private Template template;
	/**
	 * 资讯栏目
	 */
	@ManyToOne
	private Category category;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceTime() {
		return sourceTime;
	}

	public void setSourceTime(String sourceTime) {
		this.sourceTime = sourceTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Boolean getPublicity() {
		return publicity;
	}

	public void setPublicity(Boolean publicity) {
		this.publicity = publicity;
	}

	public Boolean getHome() {
		return home;
	}

	public void setHome(Boolean home) {
		this.home = home;
	}

	public Boolean getTop() {
		return top;
	}

	public void setTop(Boolean top) {
		this.top = top;
	}

	public Boolean getRecommend() {
		return recommend;
	}

	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}

	public Boolean getPublish() {
		return publish;
	}

	public void setPublish(Boolean publish) {
		this.publish = publish;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getStartPublishTime() {
		return startPublishTime;
	}

	public void setStartPublishTime(String startPublishTime) {
		this.startPublishTime = startPublishTime;
	}

	public String getEndPublishTime() {
		return endPublishTime;
	}

	public void setEndPublishTime(String endPublishTime) {
		this.endPublishTime = endPublishTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}