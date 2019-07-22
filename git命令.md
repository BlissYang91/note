[TOC]

### 在线学习

- https://learngitbranching.js.org/

### 如何解决 failed to push some refs to git

    > 刚从GitHub关联克隆下来的项目，push的时候可能会遇到这个问题，这是因为代码未能及时同步，比如远端有readme文件没有同步，执行下边两句即可推送成功

1. git pull --rebase origin master
2. git push -u origin master 或者
   git remote -v

---

### 查看状态

git status

### 全局配置

> git config --global user.name [username]
> git config --global user.email [email]

### 从远端 clone 项目到本地

> git clone 远端地址

### 从远端拉取分支到本地

> git pull origin dev(远程分支名称)

### 查看本地已有的分支

> git branch
> git branch -a 查看所有分支(包括远程分支)

### 创建本地分支

> git branch feature

### 查看分支远程分支

> git branch -r

### 删除远程分支

> git push origin --delete 分支名

### 删除本地分支

> git branch -d dev

### 强制把指定分支指向某次提交记录
> git branch -f one c3 把one分支指向c3这次的哈希提交，HEAD也一起跟随

### 本地检出一个新的分支并推送到远程仓库

- 创建本地分支,执行该指令后，会在本地创建一个新分支，该分支是从当前分支上检出的，所以所有文件内容都和当前分支一模一样，这是正常的。创建成功后，将自动切换至新分支上。
  > git checkout -b 新分支名 dev
- 推送本地分支到远程仓库

  > git push --set-upstream origin 分支名

- 将远程 git 仓库里的指定分支拉取到本地（从远程仓库里拉取一条本地不存在的分支）
  > git checkout -b 本地分支名 origin/远程分支名

> 将会自动创建一个新的本地分支，并与指定的远程分支关联起来,拉取分支前先 git fetch

### 把本地仓库里的文件提交到远程仓库里

1. 将所有有改动的文件添加进来
   > git add .
   
2. 提交刚刚添加的文件,并且必须写一句提交信息文本

   > git commit -m ""
   > git commit -am "a 表示 add"
   > git commit --amend 合并上一次commit

3. 推送到远程仓库
> git push -u origin master
> 推送成功后，日后再push，直接git push就行了，不需要后面的 -u origin master
> git push --set-upstream origin master 保证你的远程分支存在，如果不存在，也就无法进行关联
> git push -u origin master  即使远程没有你要关联的分支，它也会自动创建一个出来，以实现关联
4. 删除远程分支dev
git push origin  :dev

5. 回退到上一个版本
   git reset --hard head^
   回退到指定版本
   git reset --hard HEAD@{1}

6. 查看之前提交
   git log / git reflog

### 追踪远程分支
1.   git branch --set --upstream origin/master
2. git fetch --all 从远程获取代码到本地，不会合并
3. git reset --hard origin/master 舍弃本地，远端代码覆盖本地代码，本地分支的head被重置成远端分支的head ，本地代码会丢失
4. git fetch


### 关于 tag

- [x] 在控制台打印出当前仓库的所有标签
- git tag
- [x] 创建附注标签
- git tag -a v0.1.2 -m “0.1.2 版本”
  > 参数 a 即 annotated 的缩写，指定标签类型，后附标签名。参数 m 指定标签说明，说明信息会保存在标签对象中。
- [x] 切换到标签
- git checkout [tagname]
- [x] 查看标签信息
- git show v0.1.2
- [x] 删除标签
- git tag -d v0.1.2

### git merge 合并分支

> 将分支 feature 合并到分支 master

1. git checkout master
2. git merge feature
   > 只处理一次冲突,引入了一次合并的历史记录，合并后的所有 commit 会按照提交时间从旧到新排列
   > 所有的过程信息更多，可能会提高之后查找问题的难度

### git rebase 合并操作

> 把 feture 分支 rebase 到 maser 之后，就是相对于 master 进行变更

## 本地创建项目关联远程仓库
 >git remote add origin git@github.com:BlissYang91/hello-world.git
 > git push -u origin master

 ### 查看关联的远程仓库
 > 加-v 是带地址url的，不加就是只显示名字
 >git remote -v

## 错误处理：
1. Push rejected: Push to origin/master was rejected
> git pull --rebase origin master

> git rebase -i HEAD~2 将提交重新排序，然后把我们想要修改的提交记录挪到最前
>  commit --amend 来进行一些小修改
> 将指定分支强制移动到某个位置: git branch -f master c3  (将master分支强制移动到c3的位置)
> 将HEAD上移 gitcheckout HEAD~2 上移两个commit的位置
> 将HEAD指向另外一个父commit节点

2. 本地分支与远程失去关联 
> The current branch feature/xxxx has no upstream branch.
  To push the current branch and set the remote as upstream, use

 git push --set-upstream origin feature/xxxx
 <!-- 指定远程分支并添加关联推送 -->
 
 3. 重新关联远端
 > git remote add origin git@gitlab.xxx.com:xxx/crm_app.git
fatal: remote origin already exists.
git remote set-url origin  git@gitlab.xxx.com:xxx/crm_app.git

## git describe 的​​语法是
>git describe ref
ref 可以是任何能被 Git 识别成提交记录的引用，如果你没有指定的话，Git 会以你目前所检出的位置（HEAD）。
它输出的结果是这样的：
tag_numCommits_g hash
tag 表示的是离 ref 最近的标签， numCommits 是表示这个 ref 与 tag 相差有多少个提交记录， hash 表示的是你所给定的 ref 所表示的提交记录哈希值的前几位。
当 ref 提交记录上有某个标签时，则只输出标签名称

## 远程仓库命令
 1. git pull 就是 fetch 和 merge 的简写; git pull --rebase 就是 fetch 和 rebase 的简写！

 2. git fetch ;git rabase origin/master;git push 
 >  git fetch 更新了本地仓库中的远程分支，然后用 rebase 将工们的工作移动到最新的提交记录下，最后再用 git push 推送到远程仓库。

 3. git fetch;git merge origin/master;git push
 > git fetch 更新了本地仓库中的远程分支，然后合并了新变更到我们的本地分支（为了包含远程仓库的变更），最后我们用 git push 把工作推送到远程仓库

 4. git pull --rebase;git push
 等同于 git fetch；git rebase; git push;拉取远程最新分支到本地，将本地工作分支放在最新远程分支后边提交

 5. 创建一个本地不存在的分支totallyNotMaster追踪指定远程分支master,远程分支master不更新
 > git checkout -b totallyNotMaster origin/master

 6. 设置远程追踪分支
> git branch -u origin/master foo;
> foo 就会跟踪 origin/master,如果当前就在 foo 分支上, 还可以省略 foo：
> git branch -u origin/master;

7. 将本地指定commit分支推送到远端指定分支
> git push origin foo^:master   将本地foo分支的上一次commit提交推送到master分支

8. 将本地分支推送到一个远端不存在的分支，git会在远端创建这个分支
> git push origin master:newBranch
9. git fetch origin foo~1:bar 将远程foo分支的上一个记录位置下载到本地bar分支上，没有bar则在本地创建，本地和远端分支均不更新

10. git push origin ：foo  删除本地远程仓库的foo分支

11. fetch 创建新分支： git fetch origin ：bar 在本地创建新分支bar，远端没有此分支

12. 在本地创建了一个叫 foo 的分支，从远程仓库中的 master 分支中下载提交记录，并合并到 foo，然后再 merge 到我们的当前检出的分支上。远端分支位置不变
    git pull origin master：foo  本地创建foo分支（已经有了就不会创建），拉取远端master分支记录 合并到foo，然后将合并后的foo分支在merge到当前分支。
