[TOC]

## git 生成patch和打patch

`git diff打包的patch只能使用git apply处理。而git format-patch的补丁，可以应当使用git am命令。`
### 生成patch

- [x] git diff > xxx.patch 未提交部分
1. 只想 patch Test.java 文件

```
git diff Test.java > test.patch
```

2. 把所有的修改文件打成 patch，未提交部分

```
git diff > test.patch
```

- [x] git format-patch -x 生成最近的x次commit的patch

```
git format-patch -1            #生成最近的1次commit的patch
git format-patch -2            #生成最近的2次commit的patch
$ git format-patch HEAD^       #生成最近的1次commit的patch
$ git format-patch <r1>..<r2>  #生成两个commit间的修改的patch（生成的patch不包含r1. <r1>和<r2>都是具体的commit号)
$ git format-patch -1 <r1>     #生成单个commit的patch
$ git format-patch <r1>        #生成某commit以来的修改patch（不包含该commit）
$ git format-patch --root <r1> #生成从根到r1提交的所有patch
```

### 检查patch的情况

```
$ git apply --stat 0001-limit-log-function.patch  # 查看patch的情况
$ git apply --check 0001-limit-log-function.patch # 检查patch是否能够打上，如果没有任何输出，则说明无冲突，可以打上

```
### 应用patch
- [x] git am会直接将patch的所有信息打上去，而且不用重新git add和git commit，author也是patch的author而不是打patch的人。
- [x] git apply是另外一种打patch的命令，其与git am的区别是：git apply并不会将commit message等打上去，打完patch后需要重新git add和git commit。

1. 打patch - 使用git diff生成的patch
` git apply xxx.patch`
 
2.  打patch - 使用git format-patch生成的patch
 
```
$ git am 0001-limit-log-function.patch           # 将名字为0001-limit-log-function.patch的patch打上
$ git am --signoff 0001-limit-log-function.patch # 添加-s或者--signoff，还可以把自己的名字添加为signed off by信息，作用是注明打patch的人是谁，因为有时打patch的人并不是patch的作者
$ git am ~/patch-set/*.patch                     # 将路径~/patch-set/*.patch 按照先后顺序打上
$ git am --abort                                 # 当git am失败时，用以将已经在am过程中打上的patch废弃掉(比如有三个patch，打到第三个patch时有冲突，那么这条命令会把打上的前两个patch丢弃掉，返回没有打patch的状态)
$ git am --resolved                              # 当git am失败，解决完冲突后，这条命令会接着打patch
```
### patch冲突

```
(1) 根据git am失败的信息，找到发生冲突的具体patch文件，然后用命令git apply --reject <patch_name>，强行打这个patch，发生冲突的部分会保存为.rej文件（例如发生冲突的文件是a.txt，那么运行完这个命令后，发生conflict的部分会保存为a.txt.rej），未发生冲突的部分会成功打上patch
(2) 根据.rej文件，通过编辑该patch文件的方式解决冲突。值得注意的是，.rej文件描述的是现有的源文件与patch文件发生冲突的部分，即因为文件的哪里不一样导致patch打不上去，同时也有patch所没打上的change。关于如何读懂.rej文件，这里有一个很好的例子：https://qa.1r1g.com/sf/ask/38002681/
(3) 废弃上一条am命令已经打了的patch：git am --abort
(4) 重新打patch：git am ~/patch-set/*.patchpatch
```


###
- [x] 查看最近3次提交的文件变更记录

`git log -3 --stat
`
- [x] 查看某一次提交的文件中代码变更记录

`git show commitID`

- [x] 回退到某一个版本 彻底回退到某个版本，本地的源码也会变为上一个版本的内容
`git reset –hard：`
- [x] 只是去掉某个提交，某个patch
`git revert commitID`

### 撤销本地修改
git checkout -- *
### git stash
```
//在未add之前才能执行stash
git stash [save message] //保存，save为可选项，message为本次保存的注释

git stash list //所有保存的记录列表

git stash pop stash@{num} //恢复，num是可选项，通过git stash list可查看具体值。只能恢复一次

git stash apply stash@{num} //恢复，num是可选项，通过git stash list可查看具体值。可回复多次

git stash drop stash@{num} 删除某个保存，num是可选项，通过git stash list可查看具体值

git stash clear //删除所有保存
```
### 修改commit信息
```
git log --oneline -5 查看最近5次commit的简要信息
git commit --amend 修改最近一次的commit 信息
 git commit --amend --author=用户名  修改作者信息


生成patch：
git format-patch -M master
生成指定patch，0163bed3bf59ae74c36cc5138b4c24f1556d8304是commit id，-1是指从当前id开始，向下提交次数，包含此次且计数从1开始。
也就是说，我想要打出0163bed3bf59ae74c36cc5138b4c24f1556d8304当前的patch，则：
git format-patch 0163bed3bf59ae74c36cc5138b4c24f1556d8304 -1
想要打出0163bed3bf59ae74c36cc5138b4c24f1556d8304和它之前的一次提交的patch，则：
git format-patch 0163bed3bf59ae74c36cc5138b4c24f1556d8304 -2
生成diff：
git diff (id1) (id2) --binary --(path) > 目标文件路径
比如要生成frameworks/base/下的diff，保存到~/gittest/下的f_b.diff：（注意：旧的id1在前）
git diff 206b47c132a80870c06d87c69a548bbfeebecd2d b5ce3e4ebe9503e370d734cecc12482bca023fdf --binary -- frameworks/base/ > ~/gittest/f_b.diff


打入 patch / diff：
git apply xxx.patch
git apply xxx.diff
检查 patch / diff：
git apply --check xxx.patch
git apply --check xxx.diff
若git和需要打patch的文件不在一个目录：(git在framework下，patch要打入frameworks/base/下)
git apply --check --directory=base/ xxx.patch
git apply --directory=base/ xxx.patch
** git am 后面会说到，以及生产patch和打入patch的一些命令参数**

````


### ssh 配置
- ssh-keygen -t rsa -C "your_email@example.com"
- https://www.jianshu.com/p/601735ce7113·

### ssh 查看

```
cd ~/.ssh
yangtianfudeMacBook-Pro:.ssh yangtianfu$ git config --list
credential.helper=osxkeychain
user.name=BlissYang91
user.email=2812420513@qq.com
yangtianfudeMacBook-Pro:.ssh yangtianfu$ ls
id_rsa		id_rsa.pub	known_hosts 
 yangtianfu$ cat id_rsa.pub

yangtianfudeMacBook-Pro:.ssh yangtianfu$ 
复制上述ssh到git中即可。

```
### 如何解决 failed to push some refs to git

    > 刚从GitHub关联克隆下来的项目，push的时候可能会遇到这个问题，这是因为代码未能及时同步，比如远端有readme文件没有同步，执行下边两句即可推送成功

1. git pull --rebase origin master
2. git push -u origin master 或者
   git remote -v

---

### 查看状态
`
git status

### 全局配置

> git config --global user.name [username]
> git config --global user.email [email]

### 从远端 clone 项目到本地

> git clone 远端地址

### 从远端拉取分支到本地

> git pull origin dev(远程分支名称)
- [x] 同步远程最新分支，解决error：failed to push some refs to
> git pull --rebase origin master
> 把远程库中的更新合并到本地,rebase的作用是取消掉本地库中刚刚的commit，并把他们接到更新后的版本库之中。

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
> git branch -D dev 强制删除

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
   git reset --hard f82cfd2
`
6. 查看之前提交
   git log / git reflog

7. 真实的画出本地+远程所有分支的全部提交的树状结构
   > gitk --all
   > git log --graph --all    (不建议使用)

### 追踪远程分支
1. git branch --set-upstream-to origin dev 
2. git fetch --all 从远程获取代码到本地，不会合并
3. git reset --hard origin/master 舍弃本地，远端代码覆盖本地代码，本地分支的head被重置成远端分支的head ，本地代码会丢失
4. git fetch

### 创建一个空分支
1. git checkout --orphan 分支名
2. git rm -rf . 把当前内容全部删除
3. git commit -am "和其他分支无关联的空分支"
4. 如果没有任何文件提交的话，分支是看不到的，可以创建一个新文件后再次提交则新创建的branch就会显示出来。
使用branch来查看分支是否创建成功

git branch -a
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
- [x] 推送tag
- git push --tags 
·
### git merge 合并分支

> 将分支 feature 合并到分支 master

1. git checkout master
2. git merge feature
   > 只处理一次冲突,引入了一次合并的历史记录，合并后的所有 commit 会按照提交时间从旧到新排列
   > 所有的过程信息更多，可能会提高之后查找问题的难度

### git rebase 同步远端代码

> 1. 不更新本地master分支，直接以origin/master 为新的基准点，将新分支的修改记录在后边
   git fetch origin master   git rebase origin/master
>以上等同于： git pull origin master --rebase

### git cherry-pick
 > git cherry-pick <commitHash> 
 > 上面命令就会将指定的提交commitHash，应用于当前分支

 ```
    a - b - c - d   Master
         \
           e - f - g Feature
现在将提交f应用到master分支。


# 切换到 master 分支
$ git checkout master

# Cherry pick 操作
$ git cherry-pick f

//上面的操作完成以后，代码库就变成了下面的样子。


    a - b - c - d - f   Master
         \
           e - f - g Feature
 ```
 > git cherry-pick命令的参数，不一定是提交的哈希值，分支名也是可以的，表示转移该分支的最新提交。
 #### 转移多个提交
 ```
 git cherry-pick <HashA> <HashB>
 上面的命令将 A 和 B 两个提交应用到当前分支。这会在当前分支生成两个对应的新提交。

如果想要转移一系列的连续提交，可以使用下面的简便语法。


$ git cherry-pick A..B 
上面的命令可以转移从 A 到 B 的所有提交。它们必须按照正确的顺序放置：提交 A 必须早于提交 B，否则命令将失败，但不会报错。

注意，使用上面的命令，提交 A 将不会包含在 Cherry pick 中。如果要包含提交 A，可以使用下面的语法。


$ git cherry-pick A^..B 
 ```

## 本地创建项目关联远程仓库

 - git remote add origin git@github.com:BlissYang91/hello-world.git
 - git push -u origin master


 ### 查看关联的远程仓库
 > 加-v 是带地址url的，不加就是只显示名字
 > git remote -v

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

 <!-- 指定远程分支并添加关联推送 -->
 git push --set-upstream origin feature/xxxx

 3. error : git remote add origin git@github.com:BlissYang91/vuebase.git fatal: remote origin already exists. 
  > 解决： git remote set-url origin git@github.com:BlissYang91/vuebase.git
 
 4. 重新关联远端
 > git remote add origin git@gitlab.xxx.com:xxx/crm_app.git
fatal: remote origin already exists.
> 不用操作原来的远端分支，直接设置新的远程仓库地址
> git remote set-url origin  git@gitlab.xxx.com:xxx/crm_app.git

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
 >  git fetch 更新了本地仓库中的远程分支，然后用 rebase 将工作移动到最新的提交记录下，最后再用 git push 推送到远程仓库。

 3. git fetch;git merge origin/master;git push
 > git fetch 更新了本地仓库中的远程分支，然后合并了新变更到我们的本地分支（为了包含远程仓库的变更），最后我们用 git push 把工作推送到远程仓库

 4. git pull --rebase;git push
 等同于 git fetch；git rebase; git push;拉取远程最新分支到本地，将本地工作分支放在最新远程分支后边提交

 5. 创建一个本地不存在的分支totallyNotMaster追踪指定远程分支master,远程分支master不更新
 > git checkout -b totallyNotMaster origin/master
 > git branch --track dev1 origin/dev

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

## 关于git rebase 合并多个（以3个commit为例）commit提交
1.git rebase -i HEAD~3 进入vim编辑窗口,将要合并的commit的pick改为squash或者s
2.保存当前窗口并退出（在当前窗口按下Esc键然后:wq保存退出）
3.退出后Git会陆续压缩提交历史（commit）,如果有冲突需要修改，选择保留最新的提交历史
4. git add . 将修改添加到暂存区 
5. git rebase --continue
6.刪除本地merge临时文件


```
rm .git/MERGE_MSG
```
>如果中间执行了git rebase --abort 或者直接关闭了IDE 会产生COMMIT_EDITMSG临时文件
7. 删除不正常结束的commit临时文件
>使用rebase进行合并commit提交的时候出现了不正常退出导致.git文件夹下存在了临时文件从而无法执行git rebase --continue,解决办法就是删除此临时文件重新合并提交
```
Found a swap file by the name "D:/web/vue2/.git/.COMMIT_EDITMSG.swp"
          owned by: YTF   dated: Mon Aug 12 20:02:12 2019
         file name: /d/web/vue2/.git/COMMIT_EDITMSG
          modified: no
         user name: YTF   host name: blissyang
        process ID: 20396 (still running)
While opening file "D:/web/vue2/.git/COMMIT_EDITMSG"
             dated: Mon Aug 12 20:03:43 2019
      NEWER than swap file!
      
      Swap file "D:/web/vue2/.git/.COMMIT_EDITMSG.swp" already exists!
```




```
rm .git/COMMIT_EDITMSG
rm .git/.COMMIT_EDITMSG.sw*
```

## git忽略文件,删除远程仓库要忽略的文件（夹）

1. git rm -r --cached .idea 删除暂存区指定要忽略的文件夹 .idea
2. git rm -r --cached . 删除本地暂存区所有文件（本地文件夹不受影响）
3. 项目根目录下新建.gitignore文件，配置要删除的远程文件和文件夹关联的名字
```
*.iml
.gradle
.idea
.settings
*.jks
/effective.gradle
/gradle.properties
/local.properties
.DS_Store
/build
/captures
.externalNativeBuild

 ```
 4. git add .  重新添加暂存区文件（此时本地已经按照根目录下.gitignore文件过滤了要忽略的文件）
 5. git commit -m "删除本地要忽略的git文件关联"
 6. git push 将本地修改推送远程仓库，此时远程仓库的对应分支上就会同步本地修改，成功删除远程仓库要忽略的git文件关联。
 
 ## 从远程仓库拉取最新代码合并到本地
 //查询当前远程的版本
$ git remote -v
//获取最新代码到本地(本地当前分支为[branch]，获取的远端的分支为[origin/branch])
$ git fetch origin master  [示例1：获取远端的origin/master分支]
//查看版本差异
$ git log -p master..origin/master [示例1：查看本地master与远端origin/master的版本差异]
//合并最新代码到本地分支
$ git merge origin/master  [示例1：合并远端分支origin/master到当前分支]

- [x] 本地分支推送到github
- git init
- github上创建readme文件，看到master分支
- git remote add origin git@github.com:BlissYang91/tablayout.git
- git add . 
- git commit -m ""
- git fetch 
- git pull origin master --allow-unrelated-histories
-  git push --set-upstream origin master
-  

- [x] 查看当前用户global配置
- 	
git config --global  --list
- [x] 查看当前用户配置信息
- git config --local  --list
- [x] 配置指令
- git config
`