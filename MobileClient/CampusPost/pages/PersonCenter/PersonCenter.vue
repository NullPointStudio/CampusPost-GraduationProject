<template>
	<scroll-view>
		<div class="user_div">
			<image @click="loginClick" mode="aspectFill" src="../../static/tabBar/userImg.jpg">
			</image>
			<div>
				<text>UserName</text>
			</div>
		</div>
	</scroll-view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {
			loginClick() {
				uni.navigateTo({
					url: '/pages/Login/Login',
				})
			}
		}
	}
</script>

<style>
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
	}
</style>
