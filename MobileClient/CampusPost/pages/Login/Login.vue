<template>
	<view class="login_view">
		<u-form>
			<u-form-item>
				<u-input v-model="login_form.phone" placeholder="账号/手机号" />
			</u-form-item>
			<u-form-item>
				<u-input v-model="login_form.password" type="password" placeholder="密码" />
			</u-form-item>
			<u-button type="success" @click="login_click()">登录</u-button>
		</u-form>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				login_form: {
					phone: '',
					password: ''
				}
			}
		},
		methods: {
			login_click() {
				uni.request({
					url: this.$api + '/account/loginByPhone', //仅为示例，并非真实接口地址。
					data: this.login_form,
					method: 'POST',
					header: {
						Authorization: uni.getStorageSync('token') //自定义请求头信息
					},
					success: (res) => {
						console.log(res.data);
						if (res.data.code === 200) {
							uni.setStorageSync('token', res.data.token)
							uni.setStorageSync('userId', res.data.userId)
							uni.showToast({
								title: res.data.msg,
								icon: 'success',
								duration: 1000
							})
							let pages = getCurrentPages(); // 当前页面
							let beforePage = pages[pages.length - 2]; // 前一个页面
							uni.navigateBack({
								success: function() {
									beforePage.onLoad(); // 执行前一个页面的onLoad方法
								}
							})
						} else {
							uni.showToast({
								title: res.data.msg,
								icon: 'none',
								duration: 1000
							})
						}
					},
					fail: (e) => {
						console.log(e)
					}
				})
			}
		}
	}
</script>

<style scoped>
	.login_view {
		padding: 20rpx;
	}
</style>
