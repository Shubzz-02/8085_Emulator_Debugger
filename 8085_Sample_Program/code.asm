MVI A,0FH
MVI B,12H
SUB B
JM NEXT
STA 4000H
NEXT:CALL SBROUT
HLT

SBROUT:STA 4005H
RET