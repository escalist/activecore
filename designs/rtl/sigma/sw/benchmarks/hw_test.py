# -*- coding:utf-8 -*-
from __future__ import division

import sys

sys.path.append('../../../udm/sw')

import udm
from udm import *

import sigma
from sigma import *


udm = udm('COM31', 921600)
print("")

sigma = sigma(udm)
sigma.runtests()
