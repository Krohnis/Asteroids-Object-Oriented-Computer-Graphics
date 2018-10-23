Ricky Gutierrez

-Changed the reload key from N to U, to allow for N to be used for rejecting the quit
-Resubmission, I did not know that we had to include a case for when lives reach 0. I have set it up so that
once lives reach 0, GameWorld initilization() will be called, and will clear the Vector and reset values