[TOC]
### 在线学习
 - https://learngitbranching.js.org/
### 如何解决failed to push some refs to git
    > 刚从GitHub关联克隆下来的项目，push的时候可能会遇到这个问题，这是因为代码未能及时同步，比如远端有readme文件没有同步，执行下边两句即可推送成功
  1. git pull --rebase origin master
  2. git push -u origin master

  ---

  ### 全局配置

> git config --global user.name [username]
> git config --global user.email [email]

### 从远端clone项目到本地
> git clone 远端地址
### 从远端拉取分支到本地

>git pull origin dev(远程分支名称)

### 查看本地已有的分支
> git branch
> git branch -a 查看所有分支(包括远程分支)
### 查看分支远程分支
> git branch -r
### 删除远程分支 
> git push origin --delete 分支名
### 删除本地分支 
>git branch -d dev
### 本地检出一个新的分支并推送到远程仓库
- 创建本地分支,执行该指令后，会在本地创建一个新分支，该分支是从当前分支上检出的，所以所有文件内容都和当前分支一模一样，这是正常的。创建成功后，将自动切换至新分支上。
>git checkout -b 新分支名
- 推送本地分支到远程仓库
> git push --set-upstream origin 分支名

- 将远程git仓库里的指定分支拉取到本地（从远程仓库里拉取一条本地不存在的分支）
> git checkout -b 本地分支名 origin/远程分支名

> 将会自动创建一个新的本地分支，并与指定的远程分支关联起来,拉取分支前先 git fetch

### 把本地仓库里的文件提交到远程仓库里
1. 将所有有改动的文件添加进来
> git add .
2. 提交刚刚添加的文件,并且必须写一句提交信息文本
> git commit -m ""

3.推送到远程仓库
> git push -u origin master

> 推送成功后，日后再push，直接git push就行了，不需要后面的 -u origin master

### 关于tag
- [x] 在控制台打印出当前仓库的所有标签
- git tag 
- [x] 创建附注标签
- git tag -a v0.1.2 -m “0.1.2版本” 
> 参数a即annotated的缩写，指定标签类型，后附标签名。参数m指定标签说明，说明信息会保存在标签对象中。
- [x] 切换到标签
- git checkout [tagname]
- [x] 查看标签信息
- git show v0.1.2
- [x] 删除标签
-  git tag -d v0.1.2
### git merge合并分支
> 将分支 feature 合并到分支 master
1. git checkout master
2. git merge feature
>只处理一次冲突,引入了一次合并的历史记录，合并后的所有 commit 会按照提交时间从旧到新排列
所有的过程信息更多，可能会提高之后查找问题的难度
### git rebase合并操作
>把feture分支rebase到maser之后，就是相对于master进行变更
1. 切换到feture分支
2. 在feture分支上 git rebase
>改变当前分支从 master 上拉出分支的位置
没有多余的合并历史的记录，且合并后的 commit 顺序不一定按照 commit 的提交时间排列
可能会多次解决同一个地方的冲突（有 squash 来解决）
更清爽一些，master 分支上每个 commit 点都是相对独立完整的功能单元


