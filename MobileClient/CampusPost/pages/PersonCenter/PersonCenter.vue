<template>
	<scroll-view>
		<div class="user_div">
			<div id="mine_titles">
				<div>
					<text id="username">{{username}}</text>
				</div>
				<div>
					<text id="classname">{{classname}}</text>
				</div>
			</div>
			<div id="mine_avatar">
				<image @click="loginClick" mode="aspectFill" :src="user_avatar">
				</image>
			</div>
		</div>
		<div class="exit_btn">
			<u-button type="error" @click="exitBtn()">退出登录</u-button>
		</div>
	</scroll-view>
</template>

<script>
	export default {
		data() {
			return {
				username: '用户名',
				classname: '软件W171',
				user_avatar: '../../static/tabBar/userImg.jpg'
			}
		},
		methods: {
			loginClick() {
				if (uni.getStorageSync('token') === '' || uni.getStorageSync('token') === null) {
					uni.navigateTo({
						url: '/pages/Login/Login',
					})
				} else {
					uni.showToast({
						title: '你已经登录过了',
						icon: 'none',
						duration: 1000
					})
				}
			},
			exitBtn() {
				uni.setStorageSync('token','')
				uni.setStorageSync('userId','')
			},
			// 获取用户信息
			getUserInfo(){
				if(uni.getStorageSync('token')=== '' || uni.getStorageSync('token') === null){
					console.log("用户未登录")
				}else {
					if(uni.getStorageSync("isTeacher")===0){
						//获取学生信息
						uni.request({
						    url: this.$api + '/student/getStudentByAccountId', //仅为示例，并非真实接口地址。
							method:"GET",
						    data: {
						        account_id: uni.getStorageSync("userId")
						    },
						    header: {
						        'Authorization': uni.getStorageSync('token') //自定义请求头信息
						    },
						    success: (res) => {
						        console.log(res.data);
						        this.text = '获取学生信息';
								this.username = res.data.data.username
								this.classname = res.data.data.class_name
						    }
						});
					}else{
						//获取教师信息
					}
				}
			}
		},
		onLoad() {
			this.username = '用户名'
			this.classname = '班级'
			this.user_avatar = '../../static/tabBar/icon/user.png'
			this.getUserInfo()
			console.log('个人中心页面加载了')
			console.log('token:' + uni.getStorageSync('token'))
			console.log('token:' + uni.getStorageSync('userId'))
		}
	}
</script>

<style>
	#username{
		font-size: 40rpx;
		font-weight: bold;
	}
	#classname{
		font-size: 24rpx;
	}
	image {
		height: 120rpx;
		width: 120rpx;
		border-radius: 60rpx;
		border: #FFFFFF 2rpx solid;
		margin: auto;
	}

	.user_div {
		padding-top: 50rpx;
		padding-bottom: 50rpx;
		width: 100%;
		height: auto;
		background-color: #FFFFCC;
		text-align: center;
		display: flex;
		justify-content: space-between;
	}

	.exit_btn {
		padding: 20rpx;
	}
	#mine_titles {
		width: 50%;
		height: 100%;
		text-align: center;
	}

	#mine_avatar {
		width: 50%;
	}
</style>
