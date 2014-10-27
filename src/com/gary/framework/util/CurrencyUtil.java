package com.gary.framework.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 金额转换为人民币大写工具
 */
public final class CurrencyUtil {

	/**
	 * 数字
	 */
	private static final char[] DIGITS = new char[] { '零', '壹', '贰', '叁', '肆',
			'伍', '陆', '柒', '捌', '玖' };

	/**
	 * 单位1
	 */
	private static final String[] PREFIX = new String[] { "", "拾", "佰", "仟" };

	/**
	 * 单位2
	 */
	private static final String[] UNITS = new String[] { "", "萬", "亿", "兆", "京" };

	/**
	 * 小数单位
	 */
	private static final String FRACTIONAL_UNITS = "角分厘毫";

	private CurrencyUtil() {
	}

	/**
	 * 将人民币金额转换为大写。
	 * 
	 * @param amount
	 *            金额
	 * @return 大写
	 */
	public static final String toChineseNumerals(BigDecimal amount) {
		if (amount == null) {
			return "";
		}

		// 确定正负数
		switch (amount.signum()) {
		case -1:
			return "负" + toChineseNumerals(amount.abs());
		case 0:
			return "零圆";
		default:
		}

		// 小数部分超过支持的小数位数，需要四舍五入
		if (amount.scale() > FRACTIONAL_UNITS.length()) {
			amount = amount.setScale(FRACTIONAL_UNITS.length(),
					BigDecimal.ROUND_HALF_UP);
		}

		// 字符串缓冲
		StringBuilder buff = new StringBuilder(amount.precision() * 2 + 5);

		/* -------------------- 整数部分 -------------------- */

		// 整数
		BigInteger integer = amount.toBigInteger();
		// 整数文字
		String intText = integer.toString();
		// 整数长度
		int intLength = intText.length();

		// 刚才输出的是否是0
		boolean lastZero = true;
		String lastNonZeroUnit = null;
		for (int i = 0; i < intLength; i++) {
			// 位数（个位为0，十位为1）
			int pos = intLength - i - 1;
			// 数字
			int digit = intText.charAt(i) - '0';
			// 数字名称
			char digitName = DIGITS[digit];
			// 前缀
			String prefix = PREFIX[pos % PREFIX.length];
			// 进位
			int step = pos / PREFIX.length;
			// 单位
			String unit = UNITS[step];

			if (digit == 0) {
				if (!lastZero) {
					buff.append(digitName);
				}
				lastZero = true;
			} else {
				if (!(digit == 1 && "拾".equals(prefix))) {
					buff.append(digitName);
				}
				buff.append(prefix);
				lastNonZeroUnit = unit;
				lastZero = false;
			}

			if (pos % PREFIX.length == 0 && unit.equals(lastNonZeroUnit)) {
				if (lastZero) {
					buff.deleteCharAt(buff.length() - 1);
					lastZero = false;
				}
				buff.append(unit);
			}
		}

		if (buff.length() > 0) {
			if (buff.charAt(buff.length() - 1) == '零') {
				buff.deleteCharAt(buff.length() - 1);
			}

			buff.append("圆");
		}

		/* -------------------- 小数部分 -------------------- */

		// 小数部分，去除多余的零
		BigDecimal fraction = amount.subtract(new BigDecimal(integer));
		// 小数文字
		String fracText = fraction.toString().replaceFirst("0.", "");

		lastZero = false;
		for (int i = 0; i < fracText.length(); i++) {
			// 数字
			int digit = fracText.charAt(i) - '0';
			if (digit == 0 && lastZero) {
				continue;
			}
			// 数字名称
			char digitName = DIGITS[digit];
			// 单位
			char unit = FRACTIONAL_UNITS.charAt(i);

			if (digit != 0) {
				if (lastZero) {
					buff.append("零");
				}
				buff.append(digitName).append(unit);
			}

			lastZero = digit == 0;
		}

		return buff.toString().replaceFirst("^零", "");
	}
}
