package com.ecsail.connection;

import javax.swing.ProgressMonitor;

import com.jcraft.jsch.SftpProgressMonitor;

public class MyProgressMonitor implements SftpProgressMonitor {
	ProgressMonitor monitor;
	long count = 0;
	long max = 0;

	public void init(int op, String src, String dest, long max) {
		this.max = max;
		monitor = new ProgressMonitor(null, ((op == SftpProgressMonitor.PUT) ? "put" : "get") + ": " + src, "", 0,
				(int) max);
		count = 0;
		percent = -1;
		monitor.setProgress((int) this.count);
		monitor.setMillisToDecideToPopup(1000);
	}

	private long percent = -1;

	public boolean count(long count) {
		this.count += count;

		if (percent >= this.count * 100 / max) {
			return true;
		}
		percent = this.count * 100 / max;

		monitor.setNote("Completed " + this.count + "(" + percent + "%) out of " + max + ".");
		monitor.setProgress((int) this.count);

		return !(monitor.isCanceled());
	}

	public void end() {
		monitor.close();
	}

	private static String help = "      Available commands:\n" + "      * means unimplemented command.\n"
			+ "cd path                       Change remote directory to 'path'\n"
			+ "lcd path                      Change local directory to 'path'\n"
			+ "chgrp grp path                Change group of file 'path' to 'grp'\n"
			+ "chmod mode path               Change permissions of file 'path' to 'mode'\n"
			+ "chown own path                Change owner of file 'path' to 'own'\n"
			+ "df [path]                     Display statistics for current directory or\n"
			+ "                              filesystem containing 'path'\n"
			+ "help                          Display this help text\n" + "get remote-path [local-path]  Download file\n"
			+ "get-resume remote-path [local-path]  Resume to download file.\n"
			+ "get-append remote-path [local-path]  Append remote file to local file\n"
			+ "hardlink oldpath newpath      Hardlink remote file\n"
			+ "*lls [ls-options [path]]      Display local directory listing\n"
			+ "ln oldpath newpath            Symlink remote file\n"
			+ "*lmkdir path                  Create local directory\n"
			+ "lpwd                          Print local working directory\n"
			+ "ls [path]                     Display remote directory listing\n"
			+ "*lumask umask                 Set local umask to 'umask'\n"
			+ "mkdir path                    Create remote directory\n" + "put local-path [remote-path]  Upload file\n"
			+ "put-resume local-path [remote-path]  Resume to upload file\n"
			+ "put-append local-path [remote-path]  Append local file to remote file.\n"
			+ "pwd                           Display remote working directory\n"
			+ "stat path                     Display info about path\n" + "exit                          Quit sftp\n"
			+ "quit                          Quit sftp\n" + "rename oldpath newpath        Rename remote file\n"
			+ "rmdir path                    Remove remote directory\n"
			+ "rm path                       Delete remote file\n"
			+ "symlink oldpath newpath       Symlink remote file\n"
			+ "readlink path                 Check the target of a symbolic link\n"
			+ "realpath path                 Canonicalize the path\n"
			+ "rekey                         Key re-exchanging\n"
			+ "compression level             Packet compression will be enabled\n"
			+ "version                       Show SFTP version\n" + "?                             Synonym for help";
}
