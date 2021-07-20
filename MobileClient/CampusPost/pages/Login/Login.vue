<template>
	<view>
		<view class="cu-form-group">
			<view class="title">账号</view>
			<input type="text" placeholder="请输入账号..." name="userName" @input="userNameInput" confirm-type="done"
				confirm-hold="true"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">密码</view>
			<input type="text" placeholder="请输入密码..." name="passWord" confirm-type="done" confirm-hold="true"></input>
		</view>
		<view class="padding">
			<button class="cu-btn block bg-blue margin-tb-sm lg" @click="subBtn" type="" :disabled="isAble">
				<text v-if="loadFlag" class="cuIcon-loading2 cuIconfont-spin"></text>提交
			</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {

		}
	}
</script>

<style>
	view {
		padding: 10rpx;
	}

	uni-input {
		border: #000000 1rpx solid;
		font-size: 64rpx;
	}
</style>
