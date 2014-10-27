package com.gary.framework.util.exception;

/**
 * 需要登陆
 * @author gary
 *
 */
public class NeedLoginException extends Exception {
	
	private static final long serialVersionUID = 2638624750760431006L;

	public NeedLoginException() {
        super();
    }
	
	public NeedLoginException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NeedLoginException(String msg) {
		super(msg);
	}

	public NeedLoginException(Throwable msg) {
		super(msg);
	}

	/**
	 * 使用异常较慢的原因是:
	 * 1. 是一个synchronized方法(主要)
	 * 2. 需要填充线程运行堆栈信息
	 * 
	 * 系统业务异常其实不需要stack信息,所以干掉synchronized,
	 * 而try..catch和if...else在同一数量级,所以使用异常进行业务逻辑的控制也可
	 * 
	 * 附JDK源码
	 * 
     * Fills in the execution stack trace. This method records within this
     * <code>Throwable</code> object information about the current state of
     * the stack frames for the current thread.
     *
     * @return  a reference to this <code>Throwable</code> instance.
     * @see     java.lang.Throwable#printStackTrace()
     * 
     * 
     * public synchronized native Throwable fillInStackTrace();
     */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

}
