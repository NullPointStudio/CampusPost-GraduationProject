<template>
	<view class="printBody">
		<view>
			<div class="topic">
				<span class="block"></span>
				<span class="title">请选择打印机：</span>
			</div>
			<u-dropdown>
				<u-dropdown-item v-model="printerID" :title="printers[printerID-1].label" :options="printers">
				</u-dropdown-item>
			</u-dropdown>
		</view>
		<view class="filenameView">
			<span class="block"></span>
			<span class="title">已选择的文件：</span>
			<span>{{MyFile.tempFiles[0].name}}</span>
		</view>
		<u-button class="btns" @click="select">选择文件</u-button>
		<u-button class="btns" type="success" @click="submit">提交</u-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				MyFile: {
					tempFiles:[
						{
							name:"未选择文件"
						}
					]
				},
				printers: [{
						label: '一号楼 一楼打印机',
						value: 1,
					},
					{
						label: '二号楼 一楼打印机',
						value: 2,
					},
					{
						label: '三号楼 一楼打印机',
						value: 3,
					},
					{
						label: '四号楼 一楼打印机',
						value: 4,
					},
					{
						label: '五号楼 一楼打印机',
						value: 5,
					},
					{
						label: '六号楼 一楼打印机',
						value: 6,
					}
				],
				printerID: 1
			}
		},
		methods: {
			select() {
				let self = this
				wx.chooseMessageFile({
					count: 1,
					type: "file",
					success: function(res) {
						self.MyFile = res
						console.log("路径：",res.tempFiles[0].path)
						// self.setData({
						// 	[Myfile]: res.tempFilePaths
						// })
						// wx.uploadFile({
						// 	url: this.$api + "/print/uploadFile",
						// 	filePath: res.tempFilePaths[0],
						// 	name: res.tempFiles.name,
						// 	header: {
						// 		'Authorization': uni.getStorageSync('token') //自定义请求头信息
						// 	},
						// 	success(res){
						// 		console.log(res)
						// 	},
						// 	fail(res){
						// 		console.log("上传失败")
						// 		console.log(res)
						// 	}
						// })
					}
				})
			},
			submit() {
				console.log("选择了MyFile", this.MyFile)
				let self = this
				wx.uploadFile({
					url: self.$api + "/print/uploadFile",
					filePath: self.MyFile.tempFiles[0].path,
					name: "file",
					header: {
						Authorization: uni.getStorageSync('token') //自定义请求头信息
					},
					success(res){
						console.log(res.data)
					},
					fail(res){
						console.log("上传失败")
						console.log(res)
					}
				})
			}
		}
	}
</script>

<style>
	.title {
		margin-left: 20rpx;
	}

	.block {
		background-color: #00aaff;
		height: 20rxp;
		/* width: 5rpx; */
		padding-left: 15rpx;
	}

	.printBody {
		padding: 20rpx;
	}

	.btns {
		margin: 20rpx;
	}
	.filenameView{
		margin-top: 20rpx;
	}
</style>
