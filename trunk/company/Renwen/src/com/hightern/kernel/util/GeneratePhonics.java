/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * 根据汉字得到相应的拼音简写
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GeneratePhonics {
	
	private static LinkedHashMap spellMap = null;
	
	static {
		if (GeneratePhonics.spellMap == null) {
			GeneratePhonics.spellMap = new LinkedHashMap(400);
		}
		GeneratePhonics.initialize();
	}
	
	private static void initialize() {
		GeneratePhonics.spellPut("a", -20319);
		GeneratePhonics.spellPut("ai", -20317);
		GeneratePhonics.spellPut("an", -20304);
		GeneratePhonics.spellPut("ang", -20295);
		GeneratePhonics.spellPut("ao", -20292);
		GeneratePhonics.spellPut("ba", -20283);
		GeneratePhonics.spellPut("bai", -20265);
		GeneratePhonics.spellPut("ban", -20257);
		GeneratePhonics.spellPut("bang", -20242);
		GeneratePhonics.spellPut("bao", -20230);
		GeneratePhonics.spellPut("bei", -20051);
		GeneratePhonics.spellPut("ben", -20036);
		GeneratePhonics.spellPut("beng", -20032);
		GeneratePhonics.spellPut("bi", -20026);
		GeneratePhonics.spellPut("bian", -20002);
		GeneratePhonics.spellPut("biao", -19990);
		GeneratePhonics.spellPut("bie", -19986);
		GeneratePhonics.spellPut("bin", -19982);
		GeneratePhonics.spellPut("bing", -19976);
		GeneratePhonics.spellPut("bo", -19805);
		GeneratePhonics.spellPut("bu", -19784);
		GeneratePhonics.spellPut("ca", -19775);
		GeneratePhonics.spellPut("cai", -19774);
		GeneratePhonics.spellPut("can", -19763);
		GeneratePhonics.spellPut("cang", -19756);
		GeneratePhonics.spellPut("cao", -19751);
		GeneratePhonics.spellPut("ce", -19746);
		GeneratePhonics.spellPut("ceng", -19741);
		GeneratePhonics.spellPut("cha", -19739);
		GeneratePhonics.spellPut("chai", -19728);
		GeneratePhonics.spellPut("chan", -19725);
		GeneratePhonics.spellPut("chang", -19715);
		GeneratePhonics.spellPut("chao", -19540);
		GeneratePhonics.spellPut("che", -19531);
		GeneratePhonics.spellPut("chen", -19525);
		GeneratePhonics.spellPut("cheng", -19515);
		GeneratePhonics.spellPut("chi", -19500);
		GeneratePhonics.spellPut("chong", -19484);
		GeneratePhonics.spellPut("chou", -19479);
		GeneratePhonics.spellPut("chu", -19467);
		GeneratePhonics.spellPut("chuai", -19289);
		GeneratePhonics.spellPut("chuan", -19288);
		GeneratePhonics.spellPut("chuang", -19281);
		GeneratePhonics.spellPut("chui", -19275);
		GeneratePhonics.spellPut("chun", -19270);
		GeneratePhonics.spellPut("chuo", -19263);
		GeneratePhonics.spellPut("ci", -19261);
		GeneratePhonics.spellPut("cong", -19249);
		GeneratePhonics.spellPut("cou", -19243);
		GeneratePhonics.spellPut("cu", -19242);
		GeneratePhonics.spellPut("cuan", -19238);
		GeneratePhonics.spellPut("cui", -19235);
		GeneratePhonics.spellPut("cun", -19227);
		GeneratePhonics.spellPut("cuo", -19224);
		GeneratePhonics.spellPut("da", -19218);
		GeneratePhonics.spellPut("dai", -19212);
		GeneratePhonics.spellPut("dan", -19038);
		GeneratePhonics.spellPut("dang", -19023);
		GeneratePhonics.spellPut("dao", -19018);
		GeneratePhonics.spellPut("de", -19006);
		GeneratePhonics.spellPut("deng", -19003);
		GeneratePhonics.spellPut("di", -18996);
		GeneratePhonics.spellPut("dian", -18977);
		GeneratePhonics.spellPut("diao", -18961);
		GeneratePhonics.spellPut("die", -18952);
		GeneratePhonics.spellPut("ding", -18783);
		GeneratePhonics.spellPut("diu", -18774);
		GeneratePhonics.spellPut("dong", -18773);
		GeneratePhonics.spellPut("dou", -18763);
		GeneratePhonics.spellPut("du", -18756);
		GeneratePhonics.spellPut("duan", -18741);
		GeneratePhonics.spellPut("dui", -18735);
		GeneratePhonics.spellPut("dun", -18731);
		GeneratePhonics.spellPut("duo", -18722);
		GeneratePhonics.spellPut("e", -18710);
		GeneratePhonics.spellPut("en", -18697);
		GeneratePhonics.spellPut("er", -18696);
		GeneratePhonics.spellPut("fa", -18526);
		GeneratePhonics.spellPut("fan", -18518);
		GeneratePhonics.spellPut("fang", -18501);
		GeneratePhonics.spellPut("fei", -18490);
		GeneratePhonics.spellPut("fen", -18478);
		GeneratePhonics.spellPut("feng", -18463);
		GeneratePhonics.spellPut("fo", -18448);
		GeneratePhonics.spellPut("fou", -18447);
		GeneratePhonics.spellPut("fu", -18446);
		GeneratePhonics.spellPut("ga", -18239);
		GeneratePhonics.spellPut("gai", -18237);
		GeneratePhonics.spellPut("gan", -18231);
		GeneratePhonics.spellPut("gang", -18220);
		GeneratePhonics.spellPut("gao", -18211);
		GeneratePhonics.spellPut("ge", -18201);
		GeneratePhonics.spellPut("gei", -18184);
		GeneratePhonics.spellPut("gen", -18183);
		GeneratePhonics.spellPut("geng", -18181);
		GeneratePhonics.spellPut("gong", -18012);
		GeneratePhonics.spellPut("gou", -17997);
		GeneratePhonics.spellPut("gu", -17988);
		GeneratePhonics.spellPut("gua", -17970);
		GeneratePhonics.spellPut("guai", -17964);
		GeneratePhonics.spellPut("guan", -17961);
		GeneratePhonics.spellPut("guang", -17950);
		GeneratePhonics.spellPut("gui", -17947);
		GeneratePhonics.spellPut("gun", -17931);
		GeneratePhonics.spellPut("guo", -17928);
		GeneratePhonics.spellPut("ha", -17922);
		GeneratePhonics.spellPut("hai", -17759);
		GeneratePhonics.spellPut("han", -17752);
		GeneratePhonics.spellPut("hang", -17733);
		GeneratePhonics.spellPut("hao", -17730);
		GeneratePhonics.spellPut("he", -17721);
		GeneratePhonics.spellPut("hei", -17703);
		GeneratePhonics.spellPut("hen", -17701);
		GeneratePhonics.spellPut("heng", -17697);
		GeneratePhonics.spellPut("hong", -17692);
		GeneratePhonics.spellPut("hou", -17683);
		GeneratePhonics.spellPut("hu", -17676);
		GeneratePhonics.spellPut("hua", -17496);
		GeneratePhonics.spellPut("huai", -17487);
		GeneratePhonics.spellPut("huan", -17482);
		GeneratePhonics.spellPut("huang", -17468);
		GeneratePhonics.spellPut("hui", -17454);
		GeneratePhonics.spellPut("hun", -17433);
		GeneratePhonics.spellPut("huo", -17427);
		GeneratePhonics.spellPut("ji", -17417);
		GeneratePhonics.spellPut("jia", -17202);
		GeneratePhonics.spellPut("jian", -17185);
		GeneratePhonics.spellPut("jiang", -16983);
		GeneratePhonics.spellPut("jiao", -16970);
		GeneratePhonics.spellPut("jie", -16942);
		GeneratePhonics.spellPut("jin", -16915);
		GeneratePhonics.spellPut("jing", -16733);
		GeneratePhonics.spellPut("jiong", -16708);
		GeneratePhonics.spellPut("jiu", -16706);
		GeneratePhonics.spellPut("ju", -16689);
		GeneratePhonics.spellPut("juan", -16664);
		GeneratePhonics.spellPut("jue", -16657);
		GeneratePhonics.spellPut("jun", -16647);
		GeneratePhonics.spellPut("ka", -16474);
		GeneratePhonics.spellPut("kai", -16470);
		GeneratePhonics.spellPut("kan", -16465);
		GeneratePhonics.spellPut("kang", -16459);
		GeneratePhonics.spellPut("kao", -16452);
		GeneratePhonics.spellPut("ke", -16448);
		GeneratePhonics.spellPut("ken", -16433);
		GeneratePhonics.spellPut("keng", -16429);
		GeneratePhonics.spellPut("kong", -16427);
		GeneratePhonics.spellPut("kou", -16423);
		GeneratePhonics.spellPut("ku", -16419);
		GeneratePhonics.spellPut("kua", -16412);
		GeneratePhonics.spellPut("kuai", -16407);
		GeneratePhonics.spellPut("kuan", -16403);
		GeneratePhonics.spellPut("kuang", -16401);
		GeneratePhonics.spellPut("kui", -16393);
		GeneratePhonics.spellPut("kun", -16220);
		GeneratePhonics.spellPut("kuo", -16216);
		GeneratePhonics.spellPut("la", -16212);
		GeneratePhonics.spellPut("lai", -16205);
		GeneratePhonics.spellPut("lan", -16202);
		GeneratePhonics.spellPut("lang", -16187);
		GeneratePhonics.spellPut("lao", -16180);
		GeneratePhonics.spellPut("le", -16171);
		GeneratePhonics.spellPut("lei", -16169);
		GeneratePhonics.spellPut("leng", -16158);
		GeneratePhonics.spellPut("li", -16155);
		GeneratePhonics.spellPut("lia", -15959);
		GeneratePhonics.spellPut("lian", -15958);
		GeneratePhonics.spellPut("liang", -15944);
		GeneratePhonics.spellPut("liao", -15933);
		GeneratePhonics.spellPut("lie", -15920);
		GeneratePhonics.spellPut("lin", -15915);
		GeneratePhonics.spellPut("ling", -15903);
		GeneratePhonics.spellPut("liu", -15889);
		GeneratePhonics.spellPut("long", -15878);
		GeneratePhonics.spellPut("lou", -15707);
		GeneratePhonics.spellPut("lu", -15701);
		GeneratePhonics.spellPut("lv", -15681);
		GeneratePhonics.spellPut("luan", -15667);
		GeneratePhonics.spellPut("lue", -15661);
		GeneratePhonics.spellPut("lun", -15659);
		GeneratePhonics.spellPut("luo", -15652);
		GeneratePhonics.spellPut("ma", -15640);
		GeneratePhonics.spellPut("mai", -15631);
		GeneratePhonics.spellPut("man", -15625);
		GeneratePhonics.spellPut("mang", -15454);
		GeneratePhonics.spellPut("mao", -15448);
		GeneratePhonics.spellPut("me", -15436);
		GeneratePhonics.spellPut("mei", -15435);
		GeneratePhonics.spellPut("men", -15419);
		GeneratePhonics.spellPut("meng", -15416);
		GeneratePhonics.spellPut("mi", -15408);
		GeneratePhonics.spellPut("mian", -15394);
		GeneratePhonics.spellPut("miao", -15385);
		GeneratePhonics.spellPut("mie", -15377);
		GeneratePhonics.spellPut("min", -15375);
		GeneratePhonics.spellPut("ming", -15369);
		GeneratePhonics.spellPut("miu", -15363);
		GeneratePhonics.spellPut("mo", -15362);
		GeneratePhonics.spellPut("mou", -15183);
		GeneratePhonics.spellPut("mu", -15180);
		GeneratePhonics.spellPut("na", -15165);
		GeneratePhonics.spellPut("nai", -15158);
		GeneratePhonics.spellPut("nan", -15153);
		GeneratePhonics.spellPut("nang", -15150);
		GeneratePhonics.spellPut("nao", -15149);
		GeneratePhonics.spellPut("ne", -15144);
		GeneratePhonics.spellPut("nei", -15143);
		GeneratePhonics.spellPut("nen", -15141);
		GeneratePhonics.spellPut("neng", -15140);
		GeneratePhonics.spellPut("ni", -15139);
		GeneratePhonics.spellPut("nian", -15128);
		GeneratePhonics.spellPut("niang", -15121);
		GeneratePhonics.spellPut("niao", -15119);
		GeneratePhonics.spellPut("nie", -15117);
		GeneratePhonics.spellPut("nin", -15110);
		GeneratePhonics.spellPut("ning", -15109);
		GeneratePhonics.spellPut("niu", -14941);
		GeneratePhonics.spellPut("nong", -14937);
		GeneratePhonics.spellPut("nu", -14933);
		GeneratePhonics.spellPut("nv", -14930);
		GeneratePhonics.spellPut("nuan", -14929);
		GeneratePhonics.spellPut("nue", -14928);
		GeneratePhonics.spellPut("nuo", -14926);
		GeneratePhonics.spellPut("o", -14922);
		GeneratePhonics.spellPut("ou", -14921);
		GeneratePhonics.spellPut("pa", -14914);
		GeneratePhonics.spellPut("pai", -14908);
		GeneratePhonics.spellPut("pan", -14902);
		GeneratePhonics.spellPut("pang", -14894);
		GeneratePhonics.spellPut("pao", -14889);
		GeneratePhonics.spellPut("pei", -14882);
		GeneratePhonics.spellPut("pen", -14873);
		GeneratePhonics.spellPut("peng", -14871);
		GeneratePhonics.spellPut("pi", -14857);
		GeneratePhonics.spellPut("pian", -14678);
		GeneratePhonics.spellPut("piao", -14674);
		GeneratePhonics.spellPut("pie", -14670);
		GeneratePhonics.spellPut("pin", -14668);
		GeneratePhonics.spellPut("ping", -14663);
		GeneratePhonics.spellPut("po", -14654);
		GeneratePhonics.spellPut("pu", -14645);
		GeneratePhonics.spellPut("qi", -14630);
		GeneratePhonics.spellPut("qia", -14594);
		GeneratePhonics.spellPut("qian", -14429);
		GeneratePhonics.spellPut("qiang", -14407);
		GeneratePhonics.spellPut("qiao", -14399);
		GeneratePhonics.spellPut("qie", -14384);
		GeneratePhonics.spellPut("qin", -14379);
		GeneratePhonics.spellPut("qing", -14368);
		GeneratePhonics.spellPut("qiong", -14355);
		GeneratePhonics.spellPut("qiu", -14353);
		GeneratePhonics.spellPut("qu", -14345);
		GeneratePhonics.spellPut("quan", -14170);
		GeneratePhonics.spellPut("que", -14159);
		GeneratePhonics.spellPut("qun", -14151);
		GeneratePhonics.spellPut("ran", -14149);
		GeneratePhonics.spellPut("rang", -14145);
		GeneratePhonics.spellPut("rao", -14140);
		GeneratePhonics.spellPut("re", -14137);
		GeneratePhonics.spellPut("ren", -14135);
		GeneratePhonics.spellPut("reng", -14125);
		GeneratePhonics.spellPut("ri", -14123);
		GeneratePhonics.spellPut("rong", -14122);
		GeneratePhonics.spellPut("rou", -14112);
		GeneratePhonics.spellPut("ru", -14109);
		GeneratePhonics.spellPut("ruan", -14099);
		GeneratePhonics.spellPut("rui", -14097);
		GeneratePhonics.spellPut("run", -14094);
		GeneratePhonics.spellPut("ruo", -14092);
		GeneratePhonics.spellPut("sa", -14090);
		GeneratePhonics.spellPut("sai", -14087);
		GeneratePhonics.spellPut("san", -14083);
		GeneratePhonics.spellPut("sang", -13917);
		GeneratePhonics.spellPut("sao", -13914);
		GeneratePhonics.spellPut("se", -13910);
		GeneratePhonics.spellPut("sen", -13907);
		GeneratePhonics.spellPut("seng", -13906);
		GeneratePhonics.spellPut("sha", -13905);
		GeneratePhonics.spellPut("shai", -13896);
		GeneratePhonics.spellPut("shan", -13894);
		GeneratePhonics.spellPut("shang", -13878);
		GeneratePhonics.spellPut("shao", -13870);
		GeneratePhonics.spellPut("she", -13859);
		GeneratePhonics.spellPut("shen", -13847);
		GeneratePhonics.spellPut("sheng", -13831);
		GeneratePhonics.spellPut("shi", -13658);
		GeneratePhonics.spellPut("shou", -13611);
		GeneratePhonics.spellPut("shu", -13601);
		GeneratePhonics.spellPut("shua", -13406);
		GeneratePhonics.spellPut("shuai", -13404);
		GeneratePhonics.spellPut("shuan", -13400);
		GeneratePhonics.spellPut("shuang", -13398);
		GeneratePhonics.spellPut("shui", -13395);
		GeneratePhonics.spellPut("shun", -13391);
		GeneratePhonics.spellPut("shuo", -13387);
		GeneratePhonics.spellPut("si", -13383);
		GeneratePhonics.spellPut("song", -13367);
		GeneratePhonics.spellPut("sou", -13359);
		GeneratePhonics.spellPut("su", -13356);
		GeneratePhonics.spellPut("suan", -13343);
		GeneratePhonics.spellPut("sui", -13340);
		GeneratePhonics.spellPut("sun", -13329);
		GeneratePhonics.spellPut("suo", -13326);
		GeneratePhonics.spellPut("ta", -13318);
		GeneratePhonics.spellPut("tai", -13147);
		GeneratePhonics.spellPut("tan", -13138);
		GeneratePhonics.spellPut("tang", -13120);
		GeneratePhonics.spellPut("tao", -13107);
		GeneratePhonics.spellPut("te", -13096);
		GeneratePhonics.spellPut("teng", -13095);
		GeneratePhonics.spellPut("ti", -13091);
		GeneratePhonics.spellPut("tian", -13076);
		GeneratePhonics.spellPut("tiao", -13068);
		GeneratePhonics.spellPut("tie", -13063);
		GeneratePhonics.spellPut("ting", -13060);
		GeneratePhonics.spellPut("tong", -12888);
		GeneratePhonics.spellPut("tou", -12875);
		GeneratePhonics.spellPut("tu", -12871);
		GeneratePhonics.spellPut("tuan", -12860);
		GeneratePhonics.spellPut("tui", -12858);
		GeneratePhonics.spellPut("tun", -12852);
		GeneratePhonics.spellPut("tuo", -12849);
		GeneratePhonics.spellPut("wa", -12838);
		GeneratePhonics.spellPut("wai", -12831);
		GeneratePhonics.spellPut("wan", -12829);
		GeneratePhonics.spellPut("wang", -12812);
		GeneratePhonics.spellPut("wei", -12802);
		GeneratePhonics.spellPut("wen", -12607);
		GeneratePhonics.spellPut("weng", -12597);
		GeneratePhonics.spellPut("wo", -12594);
		GeneratePhonics.spellPut("wu", -12585);
		GeneratePhonics.spellPut("xi", -12556);
		GeneratePhonics.spellPut("xia", -12359);
		GeneratePhonics.spellPut("xian", -12346);
		GeneratePhonics.spellPut("xiang", -12320);
		GeneratePhonics.spellPut("xiao", -12300);
		GeneratePhonics.spellPut("xie", -12120);
		GeneratePhonics.spellPut("xin", -12099);
		GeneratePhonics.spellPut("xing", -12089);
		GeneratePhonics.spellPut("xiong", -12074);
		GeneratePhonics.spellPut("xiu", -12067);
		GeneratePhonics.spellPut("xu", -12058);
		GeneratePhonics.spellPut("xuan", -12039);
		GeneratePhonics.spellPut("xue", -11867);
		GeneratePhonics.spellPut("xun", -11861);
		GeneratePhonics.spellPut("ya", -11847);
		GeneratePhonics.spellPut("yan", -11831);
		GeneratePhonics.spellPut("yang", -11798);
		GeneratePhonics.spellPut("yao", -11781);
		GeneratePhonics.spellPut("ye", -11604);
		GeneratePhonics.spellPut("yi", -11589);
		GeneratePhonics.spellPut("yin", -11536);
		GeneratePhonics.spellPut("ying", -11358);
		GeneratePhonics.spellPut("yo", -11340);
		GeneratePhonics.spellPut("yong", -11339);
		GeneratePhonics.spellPut("you", -11324);
		GeneratePhonics.spellPut("yu", -11303);
		GeneratePhonics.spellPut("yuan", -11097);
		GeneratePhonics.spellPut("yue", -11077);
		GeneratePhonics.spellPut("yun", -11067);
		GeneratePhonics.spellPut("za", -11055);
		GeneratePhonics.spellPut("zai", -11052);
		GeneratePhonics.spellPut("zan", -11045);
		GeneratePhonics.spellPut("zang", -11041);
		GeneratePhonics.spellPut("zao", -11038);
		GeneratePhonics.spellPut("ze", -11024);
		GeneratePhonics.spellPut("zei", -11020);
		GeneratePhonics.spellPut("zen", -11019);
		GeneratePhonics.spellPut("zeng", -11018);
		GeneratePhonics.spellPut("zha", -11014);
		GeneratePhonics.spellPut("zhai", -10838);
		GeneratePhonics.spellPut("zhan", -10832);
		GeneratePhonics.spellPut("zhang", -10815);
		GeneratePhonics.spellPut("zhao", -10800);
		GeneratePhonics.spellPut("zhe", -10790);
		GeneratePhonics.spellPut("zhen", -10780);
		GeneratePhonics.spellPut("zheng", -10764);
		GeneratePhonics.spellPut("zhi", -10587);
		GeneratePhonics.spellPut("zhong", -10544);
		GeneratePhonics.spellPut("zhou", -10533);
		GeneratePhonics.spellPut("zhu", -10519);
		GeneratePhonics.spellPut("zhua", -10331);
		GeneratePhonics.spellPut("zhuai", -10329);
		GeneratePhonics.spellPut("zhuan", -10328);
		GeneratePhonics.spellPut("zhuang", -10322);
		GeneratePhonics.spellPut("zhui", -10315);
		GeneratePhonics.spellPut("zhun", -10309);
		GeneratePhonics.spellPut("zhuo", -10307);
		GeneratePhonics.spellPut("zi", -10296);
		GeneratePhonics.spellPut("zong", -10281);
		GeneratePhonics.spellPut("zou", -10274);
		GeneratePhonics.spellPut("zu", -10270);
		GeneratePhonics.spellPut("zuan", -10262);
		GeneratePhonics.spellPut("zui", -10260);
		GeneratePhonics.spellPut("zun", -10256);
		GeneratePhonics.spellPut("zuo", -10254);
	}
	
	private static void spellPut(String spell, int ascii) {
		GeneratePhonics.spellMap.put(spell, new Integer(ascii));
	}
	
	/**
	 * 获得单个汉字的Ascii.
	 * 
	 * @param cnchar
	 *            汉字字符
	 * @return int 错误返回 0,否则返回ascii
	 */
	private int getCnAscii(char cn) {
		final byte[] bytes = String.valueOf(cn).getBytes();
		if (bytes == null || bytes.length > 2 || bytes.length <= 0) { // 错误
			return 0;
		}
		if (bytes.length == 1) { // 英文字符
			return bytes[0];
		}
		if (bytes.length == 2) { // 中文字符
			final int hightByte = 256 + bytes[0];
			final int lowByte = 256 + bytes[1];
			final int ascii = 256 * hightByte + lowByte - 256 * 256;
			return ascii;
		}
		return 0; // 错误
	}
	
	/**
	 * 返回字符串拼音的首字母,是汉字转化为拼音,其它字符不进行转换
	 * 
	 * @param cnStr
	 *            String 字符串
	 * @return String 转换成拼音后的字符串（全大写）
	 */
	public String getFirstSpell(String cnStr) {
		if (!validate(cnStr)) { return cnStr; }
		final char[] chars = cnStr.toCharArray();
		final StringBuffer retuBuf = new StringBuffer();
		for (final char element : chars) {
			final int ascii = getCnAscii(element);// 得到单个中文的Ascii码
			if (ascii == 0) { // 取ascii时出错
				retuBuf.append(element);
			} else {
				final String spell = getSpellByAscii(ascii);
				if (spell == null) {
					retuBuf.append(element);
				} else {
					retuBuf.append(spell.substring(0, 1));
				} // end of if spell == null
			} // end of if ascii <= -20400
		} // end of for
		return retuBuf.toString().toUpperCase();
	}
	
	/**
	 * 返回字符串的全拼,是汉字转化为全拼,其它字符不进行转换
	 * 
	 * @param cnStr
	 *            String 字符串
	 * @return String 转换成全拼后的字符串
	 */
	public String getFullSpell(String cnStr) {
		if (!validate(cnStr)) { return cnStr; }
		final char[] chars = cnStr.toCharArray();
		final StringBuffer retuBuf = new StringBuffer();
		for (final char element : chars) {
			final int ascii = getCnAscii(element);// 得到单个中文的Ascii码
			if (ascii == 0) { // 取ascii时出错
				retuBuf.append(element);
			} else {
				final String spell = getSpellByAscii(ascii);
				if (spell == null) {
					retuBuf.append(element);
				} else {
					retuBuf.append(spell);
				} // end of if spell == null
			} // end of if ascii <= -20400
		} // end of for
		return retuBuf.toString();
	}
	
	/**
	 * 根据ASCII码到SpellMap中查找对应的拼音
	 * 
	 * @param asciiint
	 *            字符对应的ASCII
	 * @return String 拼音,首先判断ASCII是否>0&<160,如果是返回对应的字符, 否则到SpellMap中查找,如果没有找到拼音,则返回null,如果找到则返回拼音.
	 */
	private String getSpellByAscii(int ascii) {
		if (ascii > 0 && ascii < 160) { // 单字符--英文或半角字符
			return String.valueOf((char) ascii);
		}
		if (ascii < -20319 || ascii > -10247) { // 不知道的字符
			return null;
		}
		final Set keySet = GeneratePhonics.spellMap.keySet();
		final Iterator it = keySet.iterator();
		String spell0 = null;
		String spell = null;
		
		int asciiRang0 = -20319;
		int asciiRang;
		while (it.hasNext()) {
			spell = (String) it.next();
			final Object valObj = GeneratePhonics.spellMap.get(spell);
			if (valObj instanceof Integer) {
				asciiRang = ((Integer) valObj).intValue();
				if (ascii >= asciiRang0 && ascii < asciiRang) { // 区间找到
					return spell0 == null ? spell : spell0;
				} else {
					spell0 = spell;
					asciiRang0 = asciiRang;
				}
			}
		}
		return null;
	}
	
	/**
	 * 检验字符传是否正确
	 * 
	 * @param cnStr
	 * @return
	 */
	private boolean validate(String cnStr) {
		if (cnStr == null || cnStr.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}
	
	public static void main(String[] args) {
		final GeneratePhonics gp = new GeneratePhonics();
		System.out.println(gp.getFullSpell("海腾计算机"));
	}
}
