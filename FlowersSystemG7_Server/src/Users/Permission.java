package Users;

import java.io.Serializable;

/**
 * Enumeration
 *	Permission of user
 */
public enum Permission implements Serializable {
	Administrator,
	Limited,
	Blocked
}
