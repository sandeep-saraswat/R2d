package com.example.r2d

class User {
    var deptname: String? = null
    var email: String? = null

    constructor() {}
    constructor(name: String?, email: String?) {
        deptname = name
        this.email = email
    }
}
