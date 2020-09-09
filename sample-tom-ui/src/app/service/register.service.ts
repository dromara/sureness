import { Injectable } from '@angular/core';
import {HttpUtil} from '../util/http-util';
import {HttpParams} from '@angular/common/http';
import * as CryptoJS from 'crypto-js';

@Injectable()
export class RegisterService {

  constructor(private httpUtil: HttpUtil) { }

  getTokenKey() {
    const url = 'account/login?tokenKey=get';
    // 先向后台申请加密tokenKey tokenKey=get
    return this.httpUtil.get(url);
  }

  register(uid: string, username: string, password: string, tokenKey: string, userKey: string) {
    const url = 'account/register';
    tokenKey = CryptoJS.enc.Utf8.parse(tokenKey);
    password = CryptoJS.enc.Utf8.parse(password);
    // AES CBC加密模式
    password = CryptoJS.AES.encrypt(password, tokenKey, {iv: tokenKey, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7}).toString();
    console.log(password);

    const param = new HttpParams().append('uid', uid)
      .append('username', username)
      .append('password', password)
      .append('methodName', 'register')
      .append('userKey', userKey)
      .append('timestamp', new Date().toUTCString());

    const body = {
      'uid': uid,
      'username': username,
      'password': password,
      'methodName': 'register',
      'userKey': userKey,
      'timestamp': new Date().toUTCString()
    };

    return this.httpUtil.post(url, body);
  }
}
