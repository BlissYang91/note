adb root && adb remount
adb shell <<EOF
cd /data/data/com.service/databases
sqlite3 mock.db
.header on
.mode column
update MOCK_TABLE set SIGNAL_VALUE=4 where SIGNAL_KEY="MWCarMode";
update MOCK_TABLE set SIGNAL_EXTRA=4 where SIGNAL_KEY="MWCarMode";
update MOCK_TABLE set SIGNAL_VALUE=4 where SIGNAL_KEY="MWCarUsageMode";
update MOCK_TABLE set SIGNAL_EXTRA=4 where SIGNAL_KEY="MWCarUsageMode";
select * from MOCK_TABLE where SIGNAL_KEY="MWCarMode";
select * from MOCK_TABLE where SIGNAL_KEY="MWCarUsageMode";
EOF
