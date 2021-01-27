解码速度会提高



编码也会提高 







### 前言

不管是PC还是移动端，图片一直是流量大头。不管是在京东首页还是频道页，商品图片以及广告图片占据了大部分的流量。

评价网站性能好坏的一个主要指标就是页面响应时间，也就是说用户打开完整页面的时间。基于JPEG还有PNG图片格式的网页，其图片资源加载往往都占据了页面耗时的主要部分，那么如何保证图片质量的前提下缩小图片体积，成为了一件有价值的事情。

而如今，对JPEG、PNG以及GIF这些格式的图片已经没有太大的优化空间。但是，Google推出的WebP图片格式给图片优化提供了另一种可能。

WebP是一种支持有损压缩和无损压缩的图片文件格式，根据Google的测试，无损压缩后的WebP比PNG文件少了26％的体积，有损压缩后的WebP图片相比于等效质量指标的JPEG图片减少了25％~34%的体积。

通过研究WebP图片格式，尽可能全面地了解WebP图片的优劣势以及应用WebP图片给我们带来的收益以及风险，最终提升用户体验。

### WebP探究

京东商品图以及频道页广告图目前基本为JPG图片，以下数据主要为JPG和WebP图片的对比，测试图片采用京东商品图。

#### WebP兼容性

![img](https://img.aotu.io/FmUIiSK5GnaMRnywosGR2wARtOhx)

WebP目前支持桌面上的Chrome和Opera浏览器，手机支持仅限于原生的Android浏览器、Android系统上的Chrome浏览器、Opera Mini浏览器。

![img](https://img.aotu.io/FimDJyH6sC_n3qQeNQiVMHmBF831)

根据对目前浏览器占比与WebP的兼容性分析，如果采用WebP图片，大约有42%的用户可以直接体验到。

#### WebP命令行工具安装

Google提供了命令行工具用于将图片转换为webp。

在Mac下，可以使用homebrew安装webp工具：

```
brew install webp
```

Linux采用源码包来安装（CentOS下）：

```
yum install -y gcc make autoconf automake libtool libjpeg-devel libpng-devel# 安装编译器以及依赖包
wget https://storage.googleapis.com/downloads.webmproject.org/releases/webp/libwebp-0.5.0.tar.gz
tar -zxvf libwebp-0.5.0.tar.gz
cd libwebp-0.5.0
./configure
make
make install
```

安装完命令行工具后，就可以使用cwebp将JPG或PNG图片转换成WebP格式。

```
cwebp [-preset <...>] [options] in_file [-o out_file]
```

options参数列表中包含质量参数q，q为0～100之间的数字，比较典型的质量值大约为80。

也可以使用dwebp将WebP图片转换回PNG图片（默认）。

```
dwebp in_file [options] [-o out_file]
```

更多细节详见[使用文档](https://developers.google.com/speed/webp/docs/using)

#### WebP优势

下面我们以一张图片为例，分别用不同质量进行压缩。

![img](https://img.aotu.io/FsK4nvnPq8-LKmgUMAyQpPVzX0Wk)

WebP图片相比于JPG，拥有：

1. 更小的文件尺寸;
2. 更高的质量——与其他相同大小不同格式的压缩图像比较。

目标图像的质量和文件大小之间存在明显的折中关系。在很多情况下，可以很大程度降低图像的大小，而用户却几乎不会注意到其中的差别。

抽取100张商品图片，采用80%有损压缩，大约能减少60%的文件大小。

更多[测试](http://labs.qiang.it/wen/webp/compare.html)。

#### WebP劣势

根据Google的测试，目前WebP与JPG相比较，编码速度慢10倍，解码速度慢1.5倍。

在编码方面，一般来说，我们可以在图片上传时生成一份WebP图片或者在第一次访问JPG图片时生成WebP图片，对用户体验的影响基本忽略不计，主要问题在于1.5倍的解码速度是否会影响用户体验。

下面通过同样质量的WebP与JPG图片加载的速度进行[测试](http://labs.qiang.it/wen/webp/test.html)。测试的JPG和WebP图片大小如下：

![img](https://img.aotu.io/Fng21Plg7-00b3HKFe48nLIgP_fn)

测试数据折线图如下：

![img](https://img.aotu.io/FrvS4mf268RBStCsJSt-gbXuINrz)

从折线图可以看到，WebP虽然会增加额外的解码时间，但由于减少了文件体积，缩短了加载的时间，页面的渲染速度加快了。同时，随着图片数量的增多，WebP页面加载的速度相对JPG页面增快了。所以，使用WebP基本没有技术阻碍，还能带来性能提升以及带宽节省。