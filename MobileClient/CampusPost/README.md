# my-project

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

### 用户登录及接口请求

1. 页面强制登录
	- 完全需要登录才能访问的页面，需要做页面强制登录，做页面整体显示隐藏判断，并在加载时判断是否跳转到登录页。
2. 按钮点击登录
	- 需要登录的接口在按钮点击后跳转到登录页面。
3. 请求不登录不发送
	- 有的页面部分接口需要登录，部分不需要登录，为防止开发未作登录判断请求了需要登录的接口，需要api防护。

做法优先级 3 > 2 > 1。