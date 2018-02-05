/**
 *
 * com.toone.framework.utils  2015-9-5
 *
 */
package com.redfinger.manager.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: ShellUtils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Carson ylrainbow82@gmail.com
 * @date 2015-9-5 上午10:57:33
 * 
 */
public class ShellUtils {

	public static CommandResult execCommand(String command,
			boolean isNeedResultMsg) {
		int result = -1;
		if (StringUtils.isEmpty(command)) {
			return new CommandResult(result, null, null);
		}

		Process process = null;
		BufferedReader successResult = null;
		BufferedReader errorResult = null;
		StringBuilder successMsg = null;
		StringBuilder errorMsg = null;

		try {
			process = Runtime.getRuntime().exec(command);

			result = process.waitFor();
			// get command result
			if (isNeedResultMsg) {
				successMsg = new StringBuilder();
				errorMsg = new StringBuilder();
				successResult = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				errorResult = new BufferedReader(new InputStreamReader(
						process.getErrorStream()));
				String s;
				while ((s = successResult.readLine()) != null) {
					successMsg.append(s);
				}
				while ((s = errorResult.readLine()) != null) {
					errorMsg.append(s);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (successResult != null) {
					successResult.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (errorResult != null) {
					errorResult.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (process != null) {
				process.destroy();
			}
		}
		return new CommandResult(result, successMsg == null ? null
				: successMsg.toString(), errorMsg == null ? null
				: errorMsg.toString());
	}

	public static class CommandResult {

		/** result of command **/
		public int result;
		/** success message of command result **/
		public String successMsg;
		/** error message of command result **/
		public String errorMsg;

		public CommandResult(int result) {
			this.result = result;
		}

		public CommandResult(int result, String successMsg, String errorMsg) {
			this.result = result;
			this.successMsg = successMsg;
			this.errorMsg = errorMsg;
		}
	}
}
