package com.diyun.enums;

/**
 * In this file, defining the logging level.
 * SYS-->print some logs;
 * ALL-->print all logs.
 * 
 * @author Tx.Loooper
 * @version 2017-12-26 V1.0
 * @since 1.6
 *
 */
public enum LogLevel {
	
		SYS(1), ALL(2);	
		
        private int level;
        
        private LogLevel(int level) {
            this.level = level;
        }
        
		public int getLevel() {
			return level;
		}
		
		public void setLevel(int level) {
			this.level = level;
		}      
}
