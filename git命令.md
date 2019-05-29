[TOC]
# 在线学习
 - https://learngitbranching.js.org/
# 如何解决failed to push some refs to git
    > 刚从GitHub关联克隆下来的项目，push的时候可能会遇到这个问题，这是因为代码未能及时同步，比如远端有readme文件没有同步，执行下边两句即可推送成功
  1. git pull --rebase origin master
  2. git push -u origin master

  ---
