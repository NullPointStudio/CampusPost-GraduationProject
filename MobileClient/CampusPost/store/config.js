
// 需要永久存储，且下次APP启动需要取出的，在state中的变量名
export const saveStateKeys = ['vuex_user', 'vuex_token']

export const appTabbar = [{
  'pagePath': '/pages/index/index',
  'iconPath': '/static/tabbar/index.png',
  'selectedIconPath': '/static/tabbar/index-selected.png',
  'text': '首页'
},
{
  iconPath: '/static/tabbar/min_button.png',
  selectedIconPath: '/static/tabbar/min_button_select.png',
  text: '工具',
  midButton: true,
  pagePath: '/pages/example/js'
},
{
  'pagePath': '/pages/center/center',
  'iconPath': '/static/tabbar/center.png',
  'selectedIconPath': '/static/tabbar/center-selected.png',
  'text': '我'
}]
