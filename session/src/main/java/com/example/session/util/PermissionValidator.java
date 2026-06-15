package com.example.session.util;

import com.example.session.DAO.PermissionDAO;
import com.example.session.exceptions.DatabaseException;

public class PermissionValidator {

    public static boolean hasPermission(
            int userId,
            String permissionKey)
            throws DatabaseException {

        PermissionDAO dao = new PermissionDAO();

        return dao.hasPermission(
                userId,
                permissionKey
        );
    }
}