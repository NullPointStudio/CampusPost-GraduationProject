<template class="background">
	<!-- <view class="content">
		主页
		<u-button type="success">
			<text>{{title}} 框架真好</text>
			<u-icon name="thumb-up-fill" color="#ffffff" size="28"></u-icon>
		</u-button>
	</view> -->
	<scroll-view class="scrollview">
		<view class="wrap">
			<u-swiper :list="list"></u-swiper>
		</view>
		<div class="topic">
			<span class="block"></span>
			<span class="title">全部服务</span>
		</div>
		<u-grid :col="4" @click="click">
			<u-grid-item v-for="(item,index) in service" :index="index">
				<!-- <u-icon :name="item.iconname" :size="46"></u-icon> -->
				<image class="img1" :src="item.iconsrc"></image>
				<view class="grid-text">{{item.text}}</view>
			</u-grid-item>
		</u-grid>		
	</scroll-view>
</template>

<script>
	export default {
		data() {
			return {
				title: 'Hello',
				list: [{
						image: 'https://cdn.uviewui.com/uview/swiper/1.jpg',
						title: '昨夜星辰昨夜风，画楼西畔桂堂东'
					},
					{
						image: 'https://cdn.uviewui.com/uview/swiper/2.jpg',
						title: '身无彩凤双飞翼，心有灵犀一点通'
					},
					{
						image: 'https://cdn.uviewui.com/uview/swiper/3.jpg',
						title: '谁念西风独自凉，萧萧黄叶闭疏窗，沉思往事立残阳'
					}
				],
				service: [{
						iconsrc: "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjUiIGhlaWdodD0iMjUiIHZpZXdCb3g9IjAgMCA0OCA0OCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cGF0aCBmaWxsPSIjZmZmIiBmaWxsLW9wYWNpdHk9Ii4wMSIgZD0iTTAgMGg0OHY0OEgweiIvPjxwYXRoIGQ9Ik00OCAwSDB2NDhoNDhWMHoiIGZpbGw9IiNmZmYiIGZpbGwtb3BhY2l0eT0iLjAxIi8+PHBhdGggZD0iTTM3IDMySDExdjEyaDI2VjMyeiIgc3Ryb2tlPSIjMzMzIiBzdHJva2Utd2lkdGg9IjQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiLz48cGF0aCBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik00IDIwaDQwdjE4aC02Ljk4M3YtNkgxMC45OHY2SDRWMjB6IiBzdHJva2U9IiMzMzMiIHN0cm9rZS13aWR0aD0iNCIgc3Ryb2tlLWxpbmVjYXA9InJvdW5kIiBzdHJva2UtbGluZWpvaW49InJvdW5kIi8+PHBhdGggZD0iTTM4IDRIMTB2MTZoMjhWNHoiIHN0cm9rZT0iIzMzMyIgc3Ryb2tlLXdpZHRoPSI0IiBzdHJva2UtbGluZWpvaW49InJvdW5kIi8+PC9zdmc+",
						text: "打印机",
						path: "/pages/Printer/Printer"
					},
					{
						iconsrc: "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjUiIGhlaWdodD0iMjUiIHZpZXdCb3g9IjAgMCA0OCA0OCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cmVjdCB4PSI0IiB5PSI4IiB3aWR0aD0iNDAiIGhlaWdodD0iMzIiIHJ4PSIyIiBzdHJva2U9IiMzMzMiIHN0cm9rZS13aWR0aD0iNCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIvPjxwYXRoIGQ9Ik0xNyAyNWE0IDQgMCAxIDAgMC04IDQgNCAwIDAgMCAwIDh6IiBzdHJva2U9IiMzMzMiIHN0cm9rZS13aWR0aD0iNCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIvPjxwYXRoIGQ9Ik0yMyAzMWE2IDYgMCAwIDAtMTIgME0yOCAyMGg4TTMwIDI4aDYiIHN0cm9rZT0iIzMzMyIgc3Ryb2tlLXdpZHRoPSI0IiBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiLz48L3N2Zz4=",
						text: "补办学生证"
					},
					{
						iconsrc: "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjUiIGhlaWdodD0iMjUiIHZpZXdCb3g9IjAgMCA0OCA0OCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cGF0aCBmaWxsPSIjZmZmIiBmaWxsLW9wYWNpdHk9Ii4wMSIgZD0iTTAgMGg0OHY0OEgweiIvPjxwYXRoIGNsaXAtcnVsZT0iZXZlbm9kZCIgZD0iTTExIDE0TDI1IDR2NDBIMTFWMTR6IiBzdHJva2U9IiMzMzMiIHN0cm9rZS13aWR0aD0iNCIgc3Ryb2tlLWxpbmVjYXA9InJvdW5kIiBzdHJva2UtbGluZWpvaW49InJvdW5kIi8+PHBhdGggZD0iTTI1IDEzbDE0IDEwdjIxTTQgNDRoNDAiIHN0cm9rZT0iIzMzMyIgc3Ryb2tlLXdpZHRoPSI0IiBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiLz48L3N2Zz4=",
						text: "我的宿舍"
					},
					{
						iconsrc: "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjUiIGhlaWdodD0iMjUiIHZpZXdCb3g9IjAgMCA0OCA0OCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cGF0aCBkPSJNNCAzM2EyIDIgMCAwIDEgMi0yaDZ2LTdsMTItOCAxMiA4djdoNmEyIDIgMCAwIDEgMiAydjlhMiAyIDAgMCAxLTIgMkg0VjMzeiIgc3Ryb2tlPSIjMzMzIiBzdHJva2Utd2lkdGg9IjQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiLz48cGF0aCBkPSJNMjQgNnYxMCIgc3Ryb2tlPSIjMzMzIiBzdHJva2Utd2lkdGg9IjQiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIvPjxwYXRoIGQ9Ik0zNiAxMlY2cy0xLjUgMy02IDAtNiAwLTYgMHY2czEuNS0zIDYgMCA2IDAgNiAwek0yOCA0NFYzMWgtOHYxM00xOCA0NGgxMiIgc3Ryb2tlPSIjMzMzIiBzdHJva2Utd2lkdGg9IjQiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIvPjwvc3ZnPg==",
						text: "我的教室"
					}
				]
			}
		},
		onLoad() {

		},
		methods: {
			click(index) {
				uni.navigateTo({
					url: this.service[index].path
				})
			}
		}
	}
</script>

<style>
	.img1{
		width: 46rpx;
		height: 46rpx;
	}
	.grid-text {
		font-size: 20rpx;
		margin-top: 4rpx;
		color: #909090;
	}

	.background {
		background-color: #fafafa;
		height: 100%;
	}

	.topic {
		margin: 20rpx;
	}

	.title {
		margin-left: 20rpx;
	}

	.block {
		background-color: #00aaff;
		height: 20rxp;
		/* width: 5rpx; */
		padding-left: 15rpx;
	}

	.wrap {}

	/* .content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	} */
</style>
